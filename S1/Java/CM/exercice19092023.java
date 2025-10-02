import java.util.Scanner;

public class exercice19092023 {
    public static void main(String[] args) {
        String nomJour;
        System.out.println("Entrer le numero du jour de la semain :");

        Scanner scan = new Scanner(System.in);      
        int jour = scan.nextInt();
        scan.close();
        if(jour == 1) nomJour = "lundi";
        else if(jour == 1) nomJour = "lundi";
        else if(jour == 2) nomJour = "mardi";
        else if(jour == 3) nomJour = "mercredi";
        else if(jour == 4) nomJour = "jeudi";
        else if(jour == 5) nomJour = "vendredi";
        else if(jour == 6) nomJour = "samedi";
        else if(jour == 7) nomJour = "dimance";
        else nomJour = "Nombre invalide";

        System.out.println(nomJour);
    }
}