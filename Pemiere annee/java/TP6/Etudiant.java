import java.util.Scanner;

public class Etudiant extends Personne{
    private String numeroEtudiant;
    private String faculte;

    //Ce constructeur ne fait rien
    public Etudiant(){
    }

    //Ce constructeur copie les informations d'un etudiant e vers cet etudiant
    public Etudiant(Etudiant e){
        this.nom = new String(e.nom);
        this.age = e.age;
        this.numeroEtudiant = new String(e.numeroEtudiant);
        this.faculte = new String(e.faculte);
    }

    //Ce constructeur donne les valeurs de unNom, unAge, unNumeroEtudiant et uneFaculte a cet etudiant
    public Etudiant(String unNom, int unAge, String unNumeroEtudiant, String uneFaculte){
        this.nom = new String(unNom);
        this.age = unAge;
        this.numeroEtudiant = new String(unNumeroEtudiant);
        this.faculte = new String(uneFaculte);
    }

    //Cette fonction affiche les attributs de cet etudiant
    public String toString(){
        return 
        "Nom : " + this.nom + 
        "\nAge : " + this.age + 
        "\nNum etudiant : " + this.numeroEtudiant + 
        "\nFaculte : " + this.faculte;
    }

    //Cette fonction permet la comparaison de cet etudiant a un etudiant e
    public boolean equals(Etudiant e){
        return (this.nom.equals(e.nom) && this.age == e.age && this.numeroEtudiant.equals(e.numeroEtudiant) && this.faculte.equals(e.faculte));
    }

    //Cette fonction initialise interactivement cet etudiant
    public void init(){
        Scanner sc = new Scanner(System.in);
        this.nom = "";
        this.age = 0;
        this.numeroEtudiant = "";
        this.faculte = "";

        System.out.print("Veuillez renseigner le nom de l'etudiant : ");
        while(this.nom.equals("")){
            this.nom = sc.nextLine();
        }

        System.out.print("Veuillez renseigner l'age de l'etudiant : ");
        while(this.age == 0){
            this.age = sc.nextInt();
        }

        System.out.print("Veuillez renseigner le numero d'etudiant' : ");
        while(this.numeroEtudiant.equals("")){
            this.numeroEtudiant = sc.nextLine();
        }

        System.out.print("Veuillez renseigner le nom de la faculte de l'etudiant : ");
        while(this.faculte.equals("")){
            this.faculte = sc.nextLine();
        }
    }

    //Cette fonction retourne le nom de la faculte de cet etudiant
    public String getfaculte(){
        return this.faculte;
    }

    //Cette fonction retourne le numero d'etudiant de cet etudiant
    public String getNumeroEtudiant(){
        return this.numeroEtudiant;
    }
} //fin classe Etudiant
