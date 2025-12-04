import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Pmmedia1 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String imagePath = "file:src/images/spiderman.jpeg";
        Image image = new Image(imagePath);

        StackPane root = new StackPane();

        int levels = 5;
        double scaleFactor = 0.4;
        double blurFactor = 2.5;

        for (int i = 0; i < levels; i++) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(1008);
            imageView.setFitHeight(800);
            imageView.setPreserveRatio(true);

            imageView.setScaleX(Math.pow(scaleFactor, i));
            imageView.setScaleY(Math.pow(scaleFactor, i));

            imageView.setEffect(new GaussianBlur((5-i) * blurFactor));
            root.getChildren().add(imageView);
        }


        Scene scene = new Scene(root, 1008, 800);
        primaryStage.setTitle("Spiderman Infini :3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
