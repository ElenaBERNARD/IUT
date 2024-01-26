import java.lang.Math;
import java.util.*;

public class TP1_2024 {

    /*
     * Exercice 1 :
     * Calcul de bourse
     */
    public static void exercice1() {
        int service = -1;
        int age = -1;
        int revenue = -1;
        int enfant = -1;

        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Avez-vous fait le service militaire ? 1 : oui / 0 :Non - ");
            service = sc.nextInt();
        } while (service != 0 && service != 1);
        do {
            System.out.print("Entrez votre age - ");
            age = sc.nextInt();
        } while (age < 0);
        do {
            System.out.print("Entrez le revenue annuel de votre foyer (parents) - ");
            revenue = sc.nextInt();
        } while (revenue < 0);
        do {
            System.out.print("Entrez votre nombre d'enfant - ");
            enfant = sc.nextInt();
        } while (enfant < 0);
        if (age <= 26 + service && revenue < (1200 + 40 * enfant) * 12 || enfant >= 3) {
            System.out.println("Bourse accordée");
        } else {
            System.out.println("Bourse refusée");
        }
    }

    /*
     * Exercice 2 :
     * Affichage des annees s'ecrivant sous la forme i^2 + j^3
     */
    static void exercice2() {
        int i = 0;
        int j = 0;
        while (Math.pow(i, 2) + Math.pow(j, 3) < 2017) {
            while (Math.pow(i, 2) + Math.pow(j, 3) < 2017) {
                System.out.println((int) (Math.pow(i, 2) + Math.pow(j, 3)) + " = " + i + "^2 + " + j + "^3");
                j++;
            }
            j = 0;
            i++;
        }
    }

    /*
     * Exercice 3 :
     * Recherche de nombre parfait plus petit que n
     */
    static void exercice3(int n) {
        int s, d;
        for (; n > 1; n--) {
            s = 0;
            for (d = 1; d <= n / 2; d++) {
                if (n % d == 0)
                    s += d;
            }
            if (s == n)
                System.out.println(n);
        }
    }

    /*
     * Exercice 4 :
     * Differentes facon de rendre la monaie
     */
    static void exercice4() {
        int i = 0, j = 0, k = 0;
        while (i * 5 + j * 10 + k * 20 <= 100) {
            while (i * 5 + j * 10 + k * 20 < 100) {
                while (i * 5 + j * 10 + k * 20 < 100) {
                    if (i * 5 + j * 10 + k * 20 == 100) {
                        System.out.println("5x" + i + " + 10x" + j + " + 20x" + k + " = " + (i * 5 + j * 10 + k * 20));
                    }
                    i++;
                }
                if (i * 5 + j * 10 + k * 20 == 100) {
                    System.out.println("5x" + i + " + 10x" + j + " + 20x" + k + " = " + (i * 5 + j * 10 + k * 20));
                }
                i = 0;
                j++;
            }
            if (i * 5 + j * 10 + k * 20 == 100) {
                System.out.println("5x" + i + " + 10x" + j + " + 20x" + k + " = " + (i * 5 + j * 10 + k * 20));
            }
            j = 0;
            k++;
        }
    }

    /*
     * Exercice 5 :
     * 
     * Fonction recursive :
     * int quoiquoi (int i, int j)
     * { if (j == 0) return 1;
     * return i*quoiquoi(i,j-1);
     * }
     * 
     * Version itterative :
     */
    public static int quoiquoi(int i, int j) {
        int r = i;
        for (; j > 1; j--) {
            r = i * r;
        }
        return r;
    }

    /*
     * 5.2
     * Le programme quoiquoi calcul le carre d'un nombre n, et le renvoie
     */

    /*
     * Exercice 6 :
     * Fonction PGCD recursive
     */
    public static int exercice6(int a, int b) {
        if (a == b)
            return a;
        else if (a > b)
            return exercice6(a - b, b);
        else
            return exercice6(a, b - a);
    }

    /*
     * Exercice 7 :
     * Fonction qui renvoie le maximum d'un tableau
     */
    public static int exercice7(int[] a, int index) {
        if (index > 0) {
            return Math.max(a[index], exercice7(a, index - 1));
        } else {
            return a[0];
        }
    }

    /*
     * Exercice 8 :
     * Tri d'un tableau (tri a bulle)
     */
    static void exercice8(int[] tab) {
        int n = tab.length;
        int temp = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {
                if (tab[j - 1] > tab[j]) {
                    temp = tab[j - 1];
                    tab[j - 1] = tab[j];
                    tab[j] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // Exercice 9.1 :
        int N = 6;
        int mat[][] = new int[N][N];
        // creation de la matrice
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j)
                    mat[i][j] = 1;
                else
                    mat[i][j] = 2;
            }
        }
        // affichage de la matrice
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.print("\n");
        }

        // Exercice 9.2 :
        // creation de la matrice
        Scanner sc = new Scanner(System.in);
        System.out.print("La taille de votre matrice ");
        int taille = sc.nextInt();
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print("Entrer l'element aux coordonnees (" + i + ", " + j + ") - ");
                mat[i][j] = sc.nextInt();
            }
        }
        // mise a 0 de la deuxieme diagonal
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i + j == taille - 1)
                    mat[i][j] = 0;
            }
        }
        // affichage de la matrice
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.print("\n");
        }
    }
}