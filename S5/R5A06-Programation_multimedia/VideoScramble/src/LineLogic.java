import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.math.BigInteger;
import java.util.Arrays;

public class LineLogic {
    public static void processImage(Mat frame, int r, int s, boolean isEncrypt) {
        int height = frame.rows();
        int width = frame.cols();
        int channels = frame.channels();

        // Extraction des données brutes pour performance (évite les appels JNI lents)
        int bufferSize = (int) (frame.total() * channels);
        byte[] sourceData = new byte[bufferSize];
        frame.get(0, 0, sourceData);

        // Buffer temporaire pour l'échange des lignes
        byte[] resultData = Arrays.copyOf(sourceData, sourceData.length);

        int currentY = 0;
        int remainingHeight = height;

        // Boucle itérative sur les blocs
        while (remainingHeight > 0) {
            // Trouver la plus grande puissance de 2 <= remainingHeight
            int powerOf2 = Integer.highestOneBit(remainingHeight);

            // Si la puissance est 1, on ne peut pas mélanger une seule ligne, on avance
            if (powerOf2 < 2) break;

            // Traiter le bloc
            processBlock(sourceData, resultData, width, channels, currentY, powerOf2, r, s, isEncrypt);

            // Mise à jour pour l'itération suivante
            currentY += powerOf2;
            remainingHeight -= powerOf2;
        }

        // Remettre les données dans la Matrice OpenCV
        frame.put(0, 0, resultData);
    }

    private static void processBlock(byte[] src, byte[] dst, int width, int channels,
                                     int startY, int size, int r, int s, boolean isEncrypt) {
        int rowWidth = width * channels;

        // Pré-calcul du multiplicateur A = (2s + 1)
        // Pour le déchiffrement, on a besoin de l'inverse modulaire
        long a = 2L * s + 1;
        long a_inv = 0;

        if (!isEncrypt) {
            // Calcul de l'inverse modulaire de A modulo 'size'
            // BigInteger est pratique ici
            try {
                a_inv = BigInteger.valueOf(a)
                        .modInverse(BigInteger.valueOf(size))
                        .longValue();
            } catch (ArithmeticException e) {
                // Fallback si s est invalide (ne devrait pas arriver avec 2s+1 et size puissance de 2)
                a_inv = 1;
            }
        }

        for (int i = 0; i < size; i++) {
            int targetRowRelative;

            if (isEncrypt) {
                // Formule : dest = (r + (2s+1)*i) % size
                targetRowRelative = (int) ((r + a * i) % size);
            } else {
                // Formule Inverse : i = A^-1 * (src - r) % size
                // Attention aux nombres négatifs avec le modulo en Java
                long val = (i - r);
                targetRowRelative = (int) ((a_inv * val) % size);
                if (targetRowRelative < 0) targetRowRelative += size;
            }

            // Copie mémoire de la ligne i vers la ligne cible (ou inversement selon le point de vue)
            // Ici : src[i] va dans dst[target] pour encrypt
            // Mais pour simplifier la logique : 
            // Encrypt : La ligne 'i' de la source va à la position 'target'
            // Decrypt : On reconstruit, donc on fait l'inverse exact.

            // Pour simplifier le code, on va dire :
            // Ligne Destination = Ligne Source permutée

            int srcIndex = (startY + i) * rowWidth;

            // Si encrypt : src[i] -> dst[target]
            // Si decrypt : src[target] -> dst[i] (C'est plus simple de penser "d'où vient ce pixel")

            // Ré-implémentation stricte de votre formule :
            // Encrypt : Ligne i source va en position ((r + A*i)%size)
            if (isEncrypt) {
                int dstIndex = (startY + targetRowRelative) * rowWidth;
                System.arraycopy(src, srcIndex, dst, dstIndex, rowWidth);
            } else {
                // Decrypt : On veut savoir quelle ligne source aller chercher pour remplir la ligne i actuelle
                // C'est l'inverse de l'opération d'écriture.
                // Si Encrypt a mis X en position Y. Decrypt prend Y et le remet en X.
                // dst[i] (la version claire) doit recevoir src[cryptée]

                // La ligne cryptée à la position 'i' correspondait à quelle ligne claire ?
                // pos_crypt = (r + A * pos_claire) % size
                // i = (r + A * pos_claire) % size
                // pos_claire = A_inv * (i - r) % size

                int originalRowIndex = targetRowRelative; // C'est le calcul fait par a_inv plus haut
                int srcReadIndex = (startY + i) * rowWidth; // On lit la ligne cryptée i
                int dstWriteIndex = (startY + originalRowIndex) * rowWidth; // On l'écrit à sa place d'origine

                // Note: Pour éviter de se mélanger, l'approche la plus simple pour le decrypt 
                // est de prendre la ligne source 'i' (qui est cryptée) et de l'envoyer à sa destination décryptée.
                // Destination décryptée = A_inv * (i - r)

                int destRowDecrypted = targetRowRelative;
                int dstIndex = (startY + destRowDecrypted) * rowWidth;
                System.arraycopy(src, srcReadIndex, dst, dstIndex, rowWidth);
            }
        }
    }
}