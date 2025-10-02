import java.util.Scanner;

public class Salarie extends Personne{
    private String numeroSecu;
    private String employeur;

    //Ce constructeur ne fait rien
    public Salarie(){
    }

    //Ce constructeur copie les informations d'un salarie s vers ce salarie
    public Salarie(Salarie s){
        this.nom = new String(s.nom);
        this.age = s.age;
        this.numeroSecu = new String(s.numeroSecu);
        this.employeur = new String(s.employeur);
    }

    //Ce constructeur donne les valeurs de unNom, unAge, unNumeroSecu et unEmployeur a ce salarie
    public Salarie(String unNom, int unAge, String unNumeroSecu, String unEmployeur){
        this.nom = new String(unNom);
        this.age = unAge;
        this.numeroSecu = new String(unNumeroSecu);
        this.employeur = new String(unEmployeur);
    }

    //Cette fonction affiche les attributs de ce salarie
    public String toString(){
        return 
        "Nom : " + this.nom + 
        "\nAge : " + this.age + 
        "\nNum secu : " + this.numeroSecu + 
        "\nEmployeur : " + this.employeur;
    }

    //Cette fonction permet la comparaison de ce salarie avec un salarie s
    public boolean equals(Salarie s){
        return (this.nom.equals(s.nom) && this.age == s.age && this.numeroSecu.equals(s.numeroSecu) && this.employeur.equals(s.employeur));
    }

    //Cette fonction initialise interactivement ce salarie
    public void init(){
        Scanner sc = new Scanner(System.in);
        this.nom = "";
        this.age = 0;
        this.numeroSecu = "";
        this.employeur = "";

        System.out.print("Veuillez renseigner le nom du salarie : ");
        while(this.nom.equals("")){
            this.nom = sc.nextLine();
        }

        System.out.print("Veuillez renseigner l'age du salarie : ");
        while(this.age == 0){
            this.age = sc.nextInt();
        }

        System.out.print("Veuillez renseigner le numero de securite : ");
        while(this.numeroSecu.equals("")){
            this.numeroSecu = sc.nextLine();
        }

        System.out.print("Veuillez renseigner le nom de l'employeur : ");
        while(this.employeur.equals("")){
            this.employeur = sc.nextLine();
        }
    }

    //Cette fonction retourne l'employeur de ce salarie
    public String getEmployeur(){
        return this.employeur;
    }

    //Cette fonction retourne le numero de securite de ce salarie
    public String getNumeroSecu(){
        return this.numeroSecu;
    }
} //fin classe Salarie
