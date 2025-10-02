import java.util.Scanner;

public class Employe {
    protected static double valeurSalarial = 1334.67;

    protected String matricule;
    protected String nom;
    protected double indiceSalarial;

    public Employe() {
    }

    public Employe(String matricule, String nom, double indiceSalarial){
        this.matricule = matricule;
        this.nom = nom;
        this.indiceSalarial = indiceSalarial;
    }

    public Employe(Employe e){
        this(e.matricule, e.nom, e.indiceSalarial);
    }

    public String toString(){
        return "Matricule : " + this.matricule +
        "\nNom : " + this.nom +
        "\nIndice salarial : " + this.indiceSalarial;
    }

    public double salaire(){
        return this.indiceSalarial * valeurSalarial;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un numero de matricule : ");
        this.matricule = sc.nextLine();
        System.out.println("Entrez un nom : ");
        this.nom = sc.nextLine();
        System.out.println("Entrez un indice salarial : ");
        this.indiceSalarial = sc.nextDouble();
        sc.nextLine();
    }

    public String getNom(){
        return this.nom;
    }
}