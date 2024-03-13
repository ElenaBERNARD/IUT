import java.util.Scanner;

public class Etudiant extends Personne {
    String numeroEtudiant;
    String faculte;

    Etudiant(){
        this("", 0, "", "");
    }

    Etudiant(Etudiant s){
        this(s.nom, s.age, s.numeroEtudiant, s.faculte);
    }

    Etudiant(String nom, int age, String numeroEtudiant, String faculte){
        super(nom, age);
        this.numeroEtudiant = numeroEtudiant;
        this.faculte = faculte;
    }

    public boolean equals(Object o){
        return this.nom.equals(((Etudiant)o).nom) && 
        this.age == ((Etudiant)o).age &&
        this.faculte.equals(((Etudiant)o).faculte) &&
        this.numeroEtudiant.equals(((Etudiant)o).numeroEtudiant);
    }

    public String getFaculte(){
        return this.faculte;
    }

    public String getNumeroEtudiant(){
        return this.numeroEtudiant;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un nom : ");
        this.nom = sc.nextLine();
        System.out.println("Entrez un age : ");
        this.age = sc.nextInt();
        sc.nextLine();
        System.out.println("Entrez une faculte : ");
        this.faculte = sc.nextLine();
        System.out.println("Entrez un numero etudiant : ");
        this.numeroEtudiant = sc.nextLine();
    }

    public String toString(){
        return "Nom : " + this.nom + 
        "\nAge : " + this.age +
        "\nFaculte : " + this.faculte +
        "\nNumEtudiant : " + this.numeroEtudiant;
    }
}
