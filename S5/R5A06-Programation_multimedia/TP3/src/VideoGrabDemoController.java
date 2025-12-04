import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

/**
 * The controller for our application, implementing simple motion detection
 * on the webcam stream. Two optional sliders (if present in the FXML) allow
 * runtime tuning: threshold and minimum contour area.
 */
public class VideoGrabDemoController {
    // the FXML button
    @FXML
    private Button button;
    // the FXML image view
    @FXML
    private ImageView currentFrame;

    @FXML
    private Slider thresholdSlider; // default ~25
    @FXML
    private Slider minAreaSlider; // default ~500
    @FXML
    private Slider BlurSlider; // default ~21

    @FXML
    private CheckBox showContoursCheckBox;
    @FXML
    private CheckBox showDetectionBoxCheckBox;
    @FXML
    private CheckBox showBlurCheckBox;
    @FXML
    private CheckBox grayScaleCheckBox;


    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that realizes the video capture
    private VideoCapture capture = new VideoCapture();
    // a flag to change the button behavior
    private boolean cameraActive = false;
    // the id of the camera to be used
    private static int cameraId = 0;

    // previous grayscale frame used for frame differencing
    private Mat prevGray = new Mat();

    @FXML
    protected void startCamera(ActionEvent event) {
        if (!this.cameraActive) {
            // start the video capture
            this.capture.open(cameraId);

            // is the video stream available?
            if (this.capture.isOpened()) {
                this.cameraActive = true;

                // initialize sliders defaults if present
                if (thresholdSlider != null && thresholdSlider.getValue() == 0) {
                    thresholdSlider.setMin(1);
                    thresholdSlider.setMax(100);
                    thresholdSlider.setValue(25);
                }
                if (minAreaSlider != null && minAreaSlider.getValue() == 0) {
                    minAreaSlider.setMin(100);
                    minAreaSlider.setMax(20000);
                    minAreaSlider.setValue(500);
                }
                if (BlurSlider != null && BlurSlider.getValue() == 0) {
                    BlurSlider.setMin(1);
                    BlurSlider.setMax(51);
                    BlurSlider.setValue(21);
                    BlurSlider.setBlockIncrement(2);
                }

                // grab a frame every 33 ms (30 frames/sec)
                Runnable frameGrabber = new Runnable() {
                    @Override
                    public void run() {
                        Mat frame = grabFrame();
                        Image imageToShow = mat2Image(frame);
                        updateImageView(currentFrame, imageToShow);
                        currentFrame.setFitWidth(800);
                        currentFrame.setPreserveRatio(true);
                    }
                };

                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the button content
                this.button.setText("Stop Camera");
            } else {
                System.err.println("Impossible to open the camera connection...");
            }
        } else {
            this.cameraActive = false;
            this.button.setText("Start Camera");
            this.stopAcquisition();
        }
    }

    /**
     * Get a frame from the opened video stream and run motion detection.
     * <p>
     * Uses frame differencing between consecutive grayscale frames, thresholding,
     * dilation and contour detection. Draws bounding boxes on the original color frame.
     */
    private Mat grabFrame() {
        Mat frame = new Mat();

        if (this.capture.isOpened()) {
            try {
                // read the current frame (BGR)
                this.capture.read(frame);

                if (!frame.empty()) {
                    // parameters (read from sliders)
                    double thresholdVal = (thresholdSlider != null) ? thresholdSlider.getValue() : 25.0;
                    double minArea = (minAreaSlider != null) ? minAreaSlider.getValue() : 500.0;
                    double blurSize = (BlurSlider != null) ? BlurSlider.getValue() : 21.0;

                    // prepare gray blurred image for differencing
                    Mat gray = new Mat();
                    Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
                    Imgproc.GaussianBlur(gray, gray, new Size(blurSize, blurSize), 0);

                    if (prevGray == null || prevGray.empty()) {
                        prevGray = gray.clone();
                    } else {
                        Mat delta = new Mat();
                        Core.absdiff(prevGray, gray, delta);

                        Mat thresh = new Mat();
                        Imgproc.threshold(delta, thresh, thresholdVal, 255, Imgproc.THRESH_BINARY);
                        Imgproc.dilate(thresh, thresh, new Mat(), new Point(-1, -1), 2);

                        // find contours
                        List<MatOfPoint> contours = new ArrayList<>();
                        Imgproc.findContours(thresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                        int motionCount = 0;
                        for (MatOfPoint contour : contours) {
                            double area = Imgproc.contourArea(contour);
                            if (area < minArea) continue;
                            Rect r = Imgproc.boundingRect(contour);
                            Imgproc.rectangle(frame, r.tl(), r.br(), new Scalar(0, 255, 0), 2);
                            motionCount++;
                        }

                        if (motionCount > 0) {
                            Imgproc.putText(frame, "Motion: " + motionCount, new Point(10, 20), Imgproc.FONT_HERSHEY_SIMPLEX, 0.6, new Scalar(0, 255, 0), 2);
                        }

                        // update previous frame
                        prevGray = gray.clone();

                        // release mats
                        delta.release();
                        thresh.release();
                    }
                }

            } catch (Exception e) {
                System.err.println("Exception during the image elaboration: " + e);
            }
        }

        return frame;
    }

    private void stopAcquisition() {
        if (this.timer != null && !this.timer.isShutdown()) {
            try {
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened()) {
            this.capture.release();
        }
    }

    private void updateImageView(ImageView view, Image image) {
        onFXThread(view.imageProperty(), image);
    }

    protected void setClosed() {
        this.stopAcquisition();
    }

    private Image matToJavaFXImage(Mat mat) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".png", mat, buffer);
        return new Image(new java.io.ByteArrayInputStream(buffer.toArray()));
    }

    public static Image mat2Image(Mat frame) {
        try {
            return SwingFXUtils.toFXImage(matToBufferedImage(frame), null);
        } catch (Exception e) {
            System.err.println("Cannot convert the Mat obejct: " + e);
            return null;
        }
    }

    private static BufferedImage matToBufferedImage(Mat original) {
        if (original == null || original.empty()) {
            return new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_GRAY);
        }

        BufferedImage image = null;
        int width = original.width(), height = original.height(), channels = original.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        original.get(0, 0, sourcePixels);

        if (channels > 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        }
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.length);

        return image;
    }

    public static <T> void onFXThread(final ObjectProperty<T> property, final T value) {
        Platform.runLater(() -> {
            property.set(value);
        });
    }

}