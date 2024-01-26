import java.util.Scanner;
public class exercice09102023 {
    public static int frequence(int n, int[][] tab){
        int count = 0;
        for(int i = 0; i < tab.length; i++){
            for(int j = 0; j < tab[i].length; j++){
                if(tab[i][j] == n)
                    count ++; 
                }
            }
        return count;
    }
    public static void main(String[] args) {


        Scanner scan = new Scanner(System.in);
        int m = 0, n = 0;
        do{
            System.out.println("Entrez un nombre de lignes entre 10 et 100");
            m = scan.nextInt();
        }while (m < 10 || m > 100);
        do{
            System.out.println("Entrez un nombre de colones entre 10 et 100");
            n = scan.nextInt();
        }while (n < 10 || n > 100);

        double sum = 0;
        int max = 0; int[] idMax = {0, 0};

        int[][] tab = new int[m][n];
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                tab[i][j] = (int)(10 + (Math.random() * 90));
                sum += tab[i][j];
                if(max < tab[i][j]){
                    max = tab[i][j];
                    idMax[0] = i;
                    idMax[1] = j;
                }
            } 
        }

        System.out.println("Moyenne = " + sum/n/m);
        System.out.println("Max = " + max + ", (" + idMax[0] + ", " + idMax[1] + ")");
        System.out.println((int)sum/n/m + " apparait " + frequence((int)sum/n/m, tab) + " fois dans tab");
    }
}
