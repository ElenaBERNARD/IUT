import java.util.Scanner;
public class exercice203102023 {
    Scanner scan = new Scanner(System.in)
    static void remplirTableauRandom(int[] tab, int min, int max){
            for(int i = 0; i < tab.length; i++) tab[i] = Math.random(min, max);
    }

}
