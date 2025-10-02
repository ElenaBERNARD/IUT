import java.lang.Math;
import java.util.*;

public class TP1 {
    static void exercice1() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Avez-vous fait le service militaire ? 1 : oui / 0 :Non - ");
        int service = sc.nextInt();
        System.out.print("Entrez votre age - ");
        int age = sc.nextInt();
        System.out.print("Entrez votre revenue annuel - ");
        int revenue = sc.nextInt();
        System.out.print("Entrez votre nombre d'enfant - ");
        int enfant = sc.nextInt();
        if (age < 27 + service && revenue < (1200 + 40 * enfant) * 12 || enfant > 2) {
            System.out.print("Bourse accordée");
        } else {
            System.out.print("Bourse refusée");
        }
    }

    static void exercice2() {
        int i = 1;
        int j = 1;
        while (Math.pow(i, 2) + Math.pow(j, 3) < 2017) {
            while (Math.pow(i, 2) + Math.pow(j, 3) < 2017) {
                System.out.println((Math.pow(i, 2) + Math.pow(j, 3)) + " = " + j + "^3 " + i + "^2");
                j++;
            }
            j = 1;
            i++;
        }
    }

    static void exercice3(int n) {
        int i = 1;
        while (i < n) {
            int d = 1;
            int s = 0;
            while (d < i) {
                if (i % d == 0) {
                    s += d;
                }
                d++;
            }
            if (s == i) {
                System.out.println(i);
            }
            i++;
        }
    }

    static void exercice4() {
        int i = 0, j = 0, k = 0;
        while (i * 5 + j * 10 + k * 20 < 100) {
            while (i * 5 + j * 10 + k * 20 < 100) {
                while (i * 5 + j * 10 + k * 20 < 100) {
                    if (i * 5 + j * 10 + k * 20 == 100) {
                        System.out.println("5x" + i + " 10x" + j + " 20x" + k);
                    }
                    i++;
                }
                if (i * 5 + j * 10 + k * 20 == 100) {
                    System.out.println("5x" + i + " 10x" + j + " 20x" + k);
                }
                i = 0;
                j++;
            }
            if (i * 5 + j * 10 + k * 20 == 100) {
                System.out.println("5x" + i + " 10x" + j + " 20x" + k);
            }
            j = 0;
            k++;
        }
    }

    public static int quoiquoi(int i, int j) {
        int r = i;
        for (int k = 1; k < j; k++) {
            r = i * r;
        }
        return r;
    }

    public static int exercice6(int a, int b) {
        int PGCD = 1;
        for (int i = 1; i <= b; i++) {
            if (a % i == 0 && b % i == 0) {
                PGCD = i;
            }
        }
        return PGCD;
    }

    public static int exercice7(int[] a, int index) {
        if (index > 0) {
            return Math.max(a[index], exercice7(a, index - 1));
        } else {
            return a[0];
        }
    }

    static void exercice8(int[] arr) {
        int n = arr.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (arr[j - 1] > arr[j]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        /**
         * Exercice 1 :
         * exercice1();
         * 
         * Exercice 2 :
         * exercice2();
         * 
         * Exercice 3 :
         * exercice3(10000);
         * 
         * Exercice 4 :
         * exercice4();
         * 
         * Exercice 5 :
         * 1) La fonction retourne i^j
         * System.out.println(quoiquoi(5, 3));
         * 2) La fonction retourne n^2
         * quoiquoi(3):
         * 3*2-1 + 2*2-1 + 1*2-1 + 0 = 5 + 3 + 1 = 9
         * 
         * Exercice 6 :
         * System.out.println(exercice6(4659, 8753)); 4659 et 8753 sont premier entre
         * eux
         * 
         * int arr[] = {1, 5, 6, 9, 0, 7, 8};
         * 
         * Exercice 7 :
         * System.out.println(exercice7(arr, 6));
         * 
         * Exercice 8 :
         * exercice8(arr);
         * for(int i=0; i < arr.length; i++){
         * System.out.print(arr[i] + " ");
         * }
         */
        // // Exercice 9.1 :
        // int N = 6;
        // int mat[][] = new int[N][N];
        // // creation de la matrice
        // for (int i = 0; i < N; i++) {
        //     for (int j = 0; j < N; j++) {
        //         if (i == j)
        //             mat[i][j] = 1;
        //         else
        //             mat[i][j] = 2;
        //     }
        // }
        // // affichage de la matrice
        // for (int i = 0; i < N; i++) {
        //     for (int j = 0; j < N; j++) {
        //         System.out.print(mat[i][j] + " ");
        //     }
        //     System.out.print("\n");
        // }

        // // Exercice 9.2 :
        // // creation de la matrice
        // Scanner sc = new Scanner(System.in);
        // System.out.print("La taille de votre matrice ");
        // int taille = sc.nextInt();
        // for (int i = 0; i < taille; i++) {
        //     for (int j = 0; j < taille; j++) {
        //         System.out.print("Entrer l'element aux coordonnees (" + i + ", " + j + ") - ");
        //         mat[i][j] = sc.nextInt();
        //     }
        // }
        // // mise a 0 de la deuxieme diagonal
        // for (int i = 0; i < taille; i++) {
        //     for (int j = 0; j < taille; j++) {
        //         if (i + j == taille - 1)
        //             mat[i][j] = 0;
        //     }
        // }
        // // affichage de la matrice
        // for (int i = 0; i < taille; i++) {
        //     for (int j = 0; j < taille; j++) {
        //         System.out.print(mat[i][j] + " ");
        //     }
        //     System.out.print("\n");
        // }
        exercice2();
    }
}