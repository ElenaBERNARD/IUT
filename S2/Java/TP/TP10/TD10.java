import java.util.InputMismatchException;
import java.util.Scanner;

public class TD10 {
    static int[] tableau = {17, 12, 15, 38, 29, 157, 89, -22, 0, 5}; 
    static int division(int indice, int diviseur){ 
     return tableau[indice]/diviseur;  
    }  
    public static void main(String[] args){  
        try{
            Scanner sc = new Scanner(System.in);
            int x=-1, y=0;  
            System.out.println("Entrez l'indice de l'entier à diviser: ");  
            while(x < 0 || x >= tableau.length){
                
                    x = sc.nextInt();
                
            }
            System.out.println("Entrez le diviseur: ");
            while(y==0){
                y = sc.nextInt();
                
            }
            System.out.println("Le résultat de la division est: ");  
            System.out.println(division(x,y));  
            sc.nextLine();
        } catch(InputMismatchException e){
            System.out.println(e + "\nEntrez un entier");
        }
    }
}
