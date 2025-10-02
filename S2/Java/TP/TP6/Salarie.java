import java.util.Scanner;

public class Salarie extends Personne {
    String numeroSecu;
    String employeur;

    Salarie(){
        this("", 0, "", "");
    }

    Salarie(Salarie s){
        this(s.nom, s.age, s.numeroSecu, s.employeur);
    }

    Salarie(String nom, int age, String numeroSecu, String employeur){
        super(nom, age);
        this.numeroSecu = numeroSecu;
        this.employeur = employeur;
    }

    public boolean equals(Object o){
        return this.nom.equals(((Salarie)o).nom) && 
        this.age == ((Salarie)o).age &&
        this.employeur.equals(((Salarie)o).employeur) &&
        this.numeroSecu.equals(((Salarie)o).numeroSecu);
    }

    public String getEmployeur(){
        return this.employeur;
    }

    public String getNumeroSecu(){
        return this.numeroSecu;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un nom : ");
        this.nom = sc.nextLine();
        System.out.println("Entrez un age : ");
        this.age = sc.nextInt();
        sc.nextLine();
        System.out.println("Entrez un employeur : ");
        this.employeur = sc.nextLine();
        System.out.println("Entrez un numero de securite : ");
        this.numeroSecu = sc.nextLine();
    }

    public String toString(){
        return "Nom : " + this.nom + 
        "\nAge : " + this.age +
        "\nEmployeur : " + this.employeur +
        "\nNumSecu : " + this.numeroSecu;
    }
}
