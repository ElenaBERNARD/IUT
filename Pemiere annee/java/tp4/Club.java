import java.util.ArrayList;
import java.util.Scanner;

import javax.print.attribute.standard.NumberOfDocuments;

public class Club{
    private String nom;
    private ArrayList<Footballeur> liste;
    private ArrayList<Footballeur> equipe;

    public Club(){
        this.liste = new ArrayList<Footballeur>();
        this.equipe = new ArrayList<Footballeur>();

        Scanner scan = new Scanner(System.in);

        int n = 0;
        System.out.println("Entrer le nombre de membre du club : ");
        while(n < 11) n = scan.nextInt();

        for(int i = 0; i < n; i++){

            String nomFootballeur = "";
            System.out.println("Entrer le nom du footballeur numero " + (i+1) +" :");
            while(nomFootballeur.equals("")) nomFootballeur = scan.nextLine();

            int ind = 0;
            System.out.println("Entrer l'indice du footballeur numero " + (i+1) +" :");
            while(ind < 1) ind = scan.nextInt();

            this.liste.add(new Footballeur(nomFootballeur, ind, this));
        }

        this.nom = "";
        System.out.println("Entrer le nom du club : ");
        while(this.nom.equals("")) this.nom = scan.nextLine();

        scan.close();
    }

    public String toString(){
        String affiche = "";
        affiche += "Club : " + this.nom + "\n";
        for(int i = 0; i < this.liste.size(); i++){
            affiche += "Joueur numero " + (i+1) + " : " + this.liste.get(i).getNom() + ", Indice : " + this.liste.get(i).getIndice() + "\n";
        }
        return affiche;
    }

    public void faireEquipe(int indiceMin){
        int n = 0;
        for(int i = 0; i < this.liste.size(); i++){
            if(this.liste.get(i).getIndice() > indiceMin && n < 11){
                this.equipe.add(this.liste.get(i));
            }
            n++;
        }
    }

    public boolean estDansClub(Footballeur f){
        for(int i = 0; i < this.liste.size(); i++){
            if(f.getNom() == this.liste.get(i).getNom() && f.getIndice() == this.liste.get(i).getIndice()){
                return true;
            }
        }
        return false;
    }

    public Footballeur getListe(){
        return this.liste;
    }

    public String getNom(){
        return this.nom;
    }
}