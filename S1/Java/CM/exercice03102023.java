import java.util.Scanner;
public class exercice03102023 {
    static Scanner scan = new Scanner(System.in);

    static void remplirTableau(int[] tab){
        for(int i = 0; i < tab.length; i++) tab[i] = scan.nextInt();
    }

    static void remplirTableauRandom(int[] tab, int max){
        for(int i = 0; i < tab.length; i++) tab[i] = (int)(Math.floor(Math.random() * max));
    }

    static void affiche(int[] tab){
        for(int i = 0; i < tab.length; i++) System.out.print(tab[i] + "; ");
    }

    static double moyenne(int[] tab){
        double tot = 0;
        for(int i = 0; i < tab.length; i++) tot += tab[i];
        return tot/tab.length;
    }

    static int max(int[] tab){
        int max = tab[0];
        for(int i = 1; i < tab.length; i++) if(max < tab[i]) max = tab[i];
        return max;
    }

    static int min(int[] tab){
        int min = tab[0];
        for(int i = 1; i < tab.length; i++) if(min > tab[i]) min = tab[i];
        return min;
    }

    public static void main(String[] args) {
        int n = scan.nextInt();
        int[] tab = new int[n];
        remplirTableauRandom(tab, 100);
        affiche(tab);
    }
}
