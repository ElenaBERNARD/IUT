import java.util.Scanner;

public class Personne{  
    protected String nom;  
    protected int age; 
    
    //Ce constructeur ne fait rien
    public Personne(){
    }

    //Ce constructeur copie les informations d'une personne p vers cette personne
    public Personne(Personne p){
        this.nom = new String(p.nom);
        this.age = p.age;
    }

    //Ce constructeur donne les valeurs de unNom et unAge a cette personne
    public Personne(String unNom, int unAge){
        this.nom = new String(unNom); 
        this.age = unAge;
    }

    //Cette fonction affiche les attributs de cette personne
    public String toString(){
        return "Nom : " + this.nom + "\nAge : " + this.age;
    }

    //Cette fonction permet la comparaison de cette personne avec une personne p
    public boolean equals(Personne p){
        return (this.nom.equals(p.nom) && this.age == p.age);
    }

    //Cette fonction initialise interactivement cette personne
    public void init(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Veuillez renseigner le nom de la personne : ");
        this.nom = sc.nextLine();
        System.out.print("Veuillez renseigner l'age de la personne : ");
        this.age = sc.nextInt();
    }

    //Cette fonction retourne l'age de cette personne
    public int getAge(){
        return this.age;
    }
    
    //Cette fonction retourne le nom de cette personne
    public String getNom(){
        return this.nom;
    }
} //fin classe Personne
    
