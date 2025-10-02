import java.util.Scanner;
public class Personne {
    protected String nom;
    protected int age;

    Personne(){
        this("",0);
    }

    Personne(Personne p){
        this(p.nom, p.age);
    }

    Personne(String nom, int age){
        this.nom = nom;
        this.age = age;
    }

    public boolean equals(Object o){
        return this.nom.equals(((Personne)o).nom) && this.age == ((Personne)o).age;
    }

    public int getAge(){
        return this.age;
    }

    public String getNom(){
        return this.nom;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un nom : ");
        this.nom = sc.nextLine();
        System.out.println("Entrez un age : ");
        this.age = sc.nextInt();
    }

    public String toString(){
        return "Nom : " + this.nom + "\nAge : " + this.age;
    }
}