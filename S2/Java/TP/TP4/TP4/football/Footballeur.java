import java.util.Scanner;

public class Footballeur{
    private String nom;
    private int indice;
    private Club club;

    public Footballeur(String nom, int indice, Club club){
        this.nom = nom;
        this.indice = indice;
        this.club = club;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        this.nom = "";
        System.out.println("Entrer le nom :");
        this.nom = sc.nextLine();

        this.indice = -1;
        System.out.println("Entrer l'indice :");
        while(this.indice < 0) this.indice = sc.nextInt();
        sc.close();
    }

    public String toString(){
        return "Nom : " + this.nom +
        "Indice : " + this.indice +
        "Club : " + this.club;
    }

    public void setClub(Club club){
        this.club = club;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setIndice(int indice){
        this.indice = indice;
    }

    public Club getClub(){
        return this.club;
    }

    public String getNom(){
        return this.nom;
    }

    public int getIndice(){
        return this.indice;
    }
}