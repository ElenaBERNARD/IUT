import java.util.ArrayList;
import java.util.Scanner;

public class Club{
    private String nom;
    private ArrayList<Footballeur> liste;
    private ArrayList<Footballeur> equipe;

    public Club(){
        this.liste = new ArrayList<Footballeur>();
        this.equipe = new ArrayList<Footballeur>();

        Scanner scan = new Scanner(System.in);

        int n = 0;
        System.out.println("Entrer le nombre de membre du club (minimum 11) : ");
        while(n < 11){
            n = scan.nextInt();
        }
        scan.nextLine();

        for(int i = 0; i < n; i++){

            String nomFootballeur;
            System.out.println("Entrer le nom du footballeur numero " + (i+1) +" :");
            nomFootballeur = scan.nextLine();

            int idx = 0;
            System.out.println("Entrer l'indice du footballeur numero " + (i+1) +" :");
            while(idx < 1) idx = scan.nextInt();
            scan.nextLine();

            this.liste.add(new Footballeur(nomFootballeur, idx, this));
        }

        System.out.println("Entrer le nom du club : ");
        this.nom = scan.nextLine();
    }

    public String toString(){
        String s = "";
        s += "\nClub : " + this.nom + "\n";
        for(int i = 0; i < this.liste.size(); i++){
            s += "J" + (i+1) + " | Nom : " + this.liste.get(i).getNom() + " | Indice : " + this.liste.get(i).getIndice() + "\n";
        }
        return s;
    }

    public void faireEquipe(int indiceMin){
        for(int i = 0; i < this.liste.size(); i++){
            if(this.liste.get(i).getIndice() > indiceMin){
                this.equipe.add(this.liste.get(i));
            }
            if(this.equipe.size() >= 11){
                break;
            }
        }
    }

    public boolean estDansClub(Footballeur f){
        for(int i = 0; i < this.liste.size(); i++){
            if(f.getNom().equals(this.liste.get(i).getNom()) && f.getIndice() == this.liste.get(i).getIndice()){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Footballeur> getListe(){
        return this.liste;
    }

    public String getNom(){
        return this.nom;
    }
}