import java.util.Scanner;
public class Fraction {
    private int numerateur;
    private int denominateur;

    public Fraction(int num, int den){
        this.numerateur = num;
        this.denominateur = den;
    }

    public Fraction(Fraction f){
        this(f.numerateur, f.denominateur);
    }

    public Fraction(){
        this(1, 1);
    }

    public String toString(){
        return this.numerateur + "/" + this.denominateur;
    }

    public int getNumerateur(){
        return this.numerateur;
    }

    public int getDenominateur(){
        return this.denominateur;
    }

    public void init(){
        Scanner  sc = new Scanner(System.in);
        System.out.println("Entrez le numerateur :");
        this.numerateur = sc.nextInt();
        System.out.println("Entrez le denominateur :");
        this.denominateur = sc.nextInt();
        sc.close();
    }

    public boolean egaleA(Fraction f){
        return this.numerateur == f.numerateur && this.denominateur == f.denominateur;
    }

    public Fraction inverse(){
        return new Fraction(this.denominateur, this.numerateur);
    }

    public Fraction ajoute(Fraction f){
        int denCommun = this.denominateur * f.denominateur;
        int newNumerateur = this.numerateur * f.denominateur + f.numerateur * this.denominateur;
        return new Fraction(newNumerateur, denCommun);
    }

    public Fraction multiplie(Fraction f){
        int newDen = this.denominateur * f.denominateur;
        int newNum = this.numerateur * f.numerateur;
        return new Fraction(newNum, newDen);
    }
}
