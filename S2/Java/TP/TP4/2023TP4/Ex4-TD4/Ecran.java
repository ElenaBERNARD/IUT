import java.util.Scanner;

public class Ecran{
    private String marque;
    private double taille;

    public Ecran(){
        this.marque = "";
        this.taille = 0;
    }

    public Ecran(String m, double d){
        this.marque = m;
        this.taille = d;
    }

    public void init(){
        Scanner scan = new Scanner(System.in);
        this.marque = "";
        this.taille = 0;

        System.out.println("Entrer la marque de l'ecran :");
        while(this.marque == ""){
            this.marque = scan.nextLine();
        }

        System.out.println("Entrer la taille de l'ecran :");
        while(this.taille == 0){
            this.taille = scan.nextInt();
        }
    }

    public String toString(){
        return "    Taille : " + this.taille +
        "\n    Marque : " + this.marque;
    }
    public double getTaille(){
        return this.taille;
    }
    public String getMarque(){
        return this.marque;
    }

    public void setMarque(String s){
        this.marque = s;
    }
    public void setTaille(double T){
        this.taille = T;
    }
}
