import java.util.*;

public class Homme extends Humain {
    protected static Random random = new Random(Calendar.getInstance().getTimeInMillis());

    protected int batifolage;
    private int salaire;

    public Homme(){
    }

    public Homme(String nom) {
        super(nom);
        this.batifolage = 0;
        this.salaire = 0;
        setEsperanceVie();
    }

    public Homme(int age, int poids, String nom, int batifolage, int salaire) {
        super(age, poids, nom);
        this.batifolage = batifolage;
        this.salaire = salaire;
        setEsperanceVie();
    }

    public int compareTo(Humain h) {
        int comparison = super.compareTo(h);
        if (comparison == 0) {  
            return salaire - h.getSalaire();
        }
        return comparison;
    }

    public void print(){
        super.print();
        System.out.println("Salaire : " + salaire);
    }

    public boolean peutProcreer() {
        return this.age > 15 && this.poids < 150;
    }

    public Humain rencontre(Humain h) {
        h = (Femme) h;
        int b = loto.nextInt(100);
        if (b < this.batifolage) {
            return null;
        }
        if (h.peutProcreer() && peutProcreer()) {
            int c = loto.nextInt(100);
            if (c > h.getFertilite()) {
                return null;
            }
            int p = loto.nextInt(100);
            Humain bebe = null;
            if (p < 50) {
                bebe = new Homme("(" + this.nom + "-" + h.nom + ")");
            } else {
                bebe = new Femme("(" + this.nom + "-" + h.nom + ")");
            }
            this.grossir(loto.nextInt(20) - 10);
            h.grossir(10);
            return bebe;
        }
        return null;
    }

    public void vieillir() {
        age++;
        if (age == 18) {
            // Generate a random number between 1000 and 11000
            salaire = random.nextInt(10000) + 1000;
        }
        if (age > 15) {
            this.batifolage = loto.nextInt(30) + 70;
        } else if (age > 30) {
            this.batifolage = loto.nextInt(30) + 20;
        } else if (age > 60) {
            this.batifolage = loto.nextInt(50) + 50;
        }
        if (age <= 20)
            poids = 3 + (int) (3.6 * age);
        else if (age >= 50)
            poids += (age % 2);
    }

    protected void setEsperanceVie() {
        this.esperanceVie = loto.nextInt(30) + 50;
    }

    public boolean isHomme() {
        return true;
    }

    public boolean isFemme() {
        return false;
    }

    public int getSalaire() {
        return salaire;
    }
}