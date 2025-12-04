import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Main {
    public static void main(String[] args) {
        // Charger la bibliothèque OpenCV
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Chemin vers une image de test (remplacez par le chemin de votre image)
        String imagePath = "./images/spiderman.jpeg";

        // Lire l'image
        Mat image = Imgcodecs.imread(imagePath);
        if (image.empty()) {
            System.out.println("Erreur : Impossible de charger l'image.");
            return;
        }

        // blur image
        Imgproc.medianBlur(image, image, 9);
        Imgcodecs.imwrite(imagePath + "_blurred.png", image);
    }
}