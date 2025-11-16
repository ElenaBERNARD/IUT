public class Poule extends Volaille {
    protected static double poidsAbatage;
    protected static double prixKilo;

    public Poule(){
        super();
    }

    public Poule(double poids, int numIdentification){
        super(poids, numIdentification);
    }

    @Override
    public String toString() {
        return "Poule " + super.toString();
    }

    public double getPoidsAbatage() {
        return poidsAbatage;
    }
    public static void setPoidsAbatage(double poidsAbatage) {
        Poule.poidsAbatage = poidsAbatage;
    }
    public double getPrixKilo() {
        return prixKilo;
    }
    public static void setPrixKilo(double prixKilo) {
        Poule.prixKilo = prixKilo;
    }
}
