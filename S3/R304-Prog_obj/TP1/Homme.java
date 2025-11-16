public class Homme extends Humain {

    private int batifolage;

    Homme(String nom) {
        super(nom);
        this.batifolage = 0;
        setEsperanceVie();
    }

    Homme(int age, int poids, String nom, int batifolage) {
        super(age, poids, nom);
        this.batifolage = batifolage;
        setEsperanceVie();
    }

    public boolean peutProcreer() {
        return this.age > 15 && this.poids < 150;
    }

    public Humain rencontre(Femme f) {
        int b = loto.nextInt(100);
        if (b < this.batifolage) {
            return null;
        }
        if (f.peutProcreer() && peutProcreer()) {
            int c = loto.nextInt(100);
            if (c > f.getFertilite()) {
                return null;
            }
            int p = loto.nextInt(100);
            Humain bebe = null;
            if (p < 50) {
                bebe = new Homme("(" + this.nom + "-" + f.nom + ")");
            } else {
                bebe = new Femme("(" + this.nom + "-" + f.nom + ")");
            }
            this.grossir(loto.nextInt(20) - 10);
            f.grossir(10);
            return bebe;
        }
        return null;
    }

    public void vieillir() {
        age++;
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
}