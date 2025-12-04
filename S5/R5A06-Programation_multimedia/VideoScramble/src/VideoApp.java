import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class VideoApp extends Application {

    // UI Elements
    private ImageView originalView; // Optionnel, pour debug
    private ImageView encryptedView;
    private ImageView decryptedView;

    // Inputs Encrypt
    private TextField rEncField = new TextField("50");
    private TextField sEncField = new TextField("10");

    // Inputs Decrypt
    private TextField rDecField = new TextField("50");
    private TextField sDecField = new TextField("10");

    private CheckBox lockCheckbox = new CheckBox("Lock Keys (Sync)");

    // OpenCV
    private VideoCapture capture;
    private ScheduledExecutorService timer;
    private boolean cameraActive = false;

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Video Scramble");

        GridPane controls = new GridPane();
        controls.setPadding(new Insets(10));
        controls.setHgap(10);
        controls.setVgap(10);
        controls.setAlignment(Pos.CENTER);

        controls.add(new Label("Clé Encryption (R, S):"), 0, 0);
        controls.add(new Label("R:"), 1, 0);
        controls.add(rEncField, 2, 0);
        controls.add(new Label("S:"), 3, 0);
        controls.add(sEncField, 4, 0);

        controls.add(new Label("Clé Décryption (R, S):"), 0, 1);
        controls.add(new Label("R:"), 1, 1);
        controls.add(rDecField, 2, 1);
        controls.add(new Label("S:"), 3, 1);
        controls.add(sDecField, 4, 1);

        controls.add(lockCheckbox, 5, 1);

        configureInputs();

        encryptedView = new ImageView();
        encryptedView.setFitWidth(480);
        encryptedView.setPreserveRatio(true);

        decryptedView = new ImageView();
        decryptedView.setFitWidth(480);
        decryptedView.setPreserveRatio(true);

        HBox videoBox = new HBox(20,
                new VBox(5, new Label("Vidéo Chiffrée"), encryptedView),
                new VBox(5, new Label("Vidéo Déchiffrée"), decryptedView)
        );
        videoBox.setAlignment(Pos.CENTER);
        videoBox.setPadding(new Insets(15));

        BorderPane root = new BorderPane();
        root.setBottom(controls);
        root.setCenter(videoBox);

        startVideo();

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> stopAcquisition());
        primaryStage.show();
    }

    private void configureInputs() {
        lockCheckbox.setSelected(true);
        rDecField.setDisable(true);
        sDecField.setDisable(true);

        lockCheckbox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            rDecField.setDisable(newVal);
            sDecField.setDisable(newVal);
            if (newVal) {
                rDecField.setText(rEncField.getText());
                sDecField.setText(sEncField.getText());
            }
        });

        // Synchronisation si lock est activé
        rEncField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (lockCheckbox.isSelected()) rDecField.setText(newVal);
        });
        sEncField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (lockCheckbox.isSelected()) sDecField.setText(newVal);
        });
    }

    private void startVideo() {
        // Test avec un fichier local
        String filename = "videos/cat.avi"; // Nom simple pour tester à la racine

        java.io.File file = new java.io.File(filename);

        System.out.println("--- DEBUG PATH ---");
        System.out.println("Java cherche ici : " + System.getProperty("user.dir"));
        System.out.println("Chemin absolu tenté : " + file.getAbsolutePath());
        System.out.println("Le fichier existe-t-il ? : " + file.exists());
        System.out.println("------------------");

        if (!file.exists()) {
            System.err.println("ERREUR : Le fichier vidéo n'est pas trouvé !");
            return;
        }

        capture = new VideoCapture(0); // On force le chemin absolu calculé

        if (capture.isOpened()) {
            System.out.println("Succès : Vidéo ouverte !");
            cameraActive = true;
            Runnable frameGrabber = this::grabFrame;
            timer = Executors.newSingleThreadScheduledExecutor();
            timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
        } else {
            System.err.println("ERREUR CRITIQUE : Le fichier est trouvé mais OpenCV n'arrive pas à l'ouvrir.");
            System.err.println("Solutions possibles :");
            System.err.println("1. Codec non supporté (essayez un .avi simple).");
            System.err.println("2. DLL ffmpeg manquante (opencv_videoio_ffmpeg*.dll).");
        }
    }

    private void grabFrame() {
        if (capture.isOpened()) {
            Mat frame = new Mat();
            if (capture.read(frame)) {
                Mat encryptedFrame = frame.clone();

                int rEnc = parseSafe(rEncField.getText());
                int sEnc = parseSafe(sEncField.getText());

                LineLogic.processImage(encryptedFrame, rEnc, sEnc, true);

                Mat decryptedFrame = encryptedFrame.clone();

                int rDec = parseSafe(rDecField.getText());
                int sDec = parseSafe(sDecField.getText());

                LineLogic.processImage(decryptedFrame, rDec, sDec, false);

                Image imageEnc = mat2Image(encryptedFrame);
                Image imageDec = mat2Image(decryptedFrame);

                Platform.runLater(() -> {
                    encryptedView.setImage(imageEnc);
                    decryptedView.setImage(imageDec);
                });

                frame.release();
                encryptedFrame.release();
                decryptedFrame.release();
            } else {
                capture.set(org.opencv.videoio.Videoio.CAP_PROP_POS_FRAMES, 0);
            }
        }
    }

    private int parseSafe(String txt) {
        try { return Integer.parseInt(txt); } catch (Exception e) { return 0; }
    }

    private Image mat2Image(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", frame, buffer);
        return new Image(new ByteArrayInputStream(buffer.toArray()));
    }

    private void stopAcquisition() {
        if (timer != null && !timer.isShutdown()) {
            timer.shutdown();
            try { timer.awaitTermination(33, TimeUnit.MILLISECONDS); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        if (capture != null && capture.isOpened()) {
            capture.release();
        }
    }
}