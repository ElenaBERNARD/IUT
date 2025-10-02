import java.util.Scanner;

public class Processeur {
    private String marque;
    private double frequence;
    
    public Processeur(){
        this.marque = "";
        this.frequence = 0;
    }

    public Processeur(String m, double d){
        this.marque = m;
        this.frequence = d;
    }

    public void init(){
        Scanner scan = new Scanner(System.in);
        this.frequence = 0;

        System.out.println("Entrer la marque du processeur :");
        
        this.marque = scan.nextLine();

        System.out.println("Entrer la frequence du processeur :");
        while(this.frequence == 0){
            this.frequence = scan.nextInt();
        }
    }

    public String toString(){
        return "    Frequence : " + this.frequence +
        "\n    Marque : " + this.marque;
    }

    public double getFrequence(){
        return this.frequence;
    }

    public String getMarque(){
        return this.marque;
    }

    public void setMarque(String s){
        this.marque = s;
    }
    public void setFrequence(double T){
        this.frequence = T;
    }
}
