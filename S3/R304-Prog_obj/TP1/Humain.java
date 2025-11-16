import java.util.*;

public class Humain {

    protected static Random loto = new Random(Calendar.getInstance().getTimeInMillis());
    protected int age;
    protected int poids;
    protected String nom;
    protected int esperanceVie;

    Humain(String nom) {
        this.nom = nom;
        this.age = 0;
        this.poids = 3;
        this.esperanceVie = 70;
    }

    Humain(int age, int poids, String nom) {
        this.nom = nom;
        this.age = age;
        this.poids = poids;
        this.esperanceVie = 70;
    }

    void setNom(String nom) {
        this.nom = nom;
    }

    void setAge(int age) {
        this.age = age;
    }

    void setPoids(int poids) {
        this.poids = poids;
    }

    int getAge() {
        return this.age;
    }

    int getPoids() {
        return this.poids;
    }

    String getNom() {
        return this.nom;
    }

    protected void setEsperanceVie() {
        this.esperanceVie = 70;
    }

    public void vieillir() {
        age++;
    }

    public void grossir(int p) {
        if (this.poids - p > 0)
            this.poids += p;
    }

    public boolean isDead() {
        return (this.age > this.esperanceVie);
    }

    public void print() {
        System.out.println(
                "Nom : " + this.nom +
                        "\nAge : " + this.age +
                        "\nPoids : " + this.poids +
                        "\nEsperance de vie : " + this.esperanceVie);
    }

    public boolean isHomme() {
        return false;
    }

    public boolean isFemme() {
        return false;
    }

    public boolean peutProcreer() {
        return false;
    }
}