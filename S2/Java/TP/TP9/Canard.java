public class Canard extends Volaille {
    protected static double poidsAbatage;
    protected static double prixKilo;

    public Canard(){
        super();
    }

    public Canard(double poids, int numIdentification){
        super(poids, numIdentification);
    }

    @Override
    public String toString() {
        return "Canard " + super.toString();
    }

    public double getPoidsAbatage() {
        return poidsAbatage;
    }
    public static void setPoidsAbatage(double poidsAbatage) {
        Canard.poidsAbatage = poidsAbatage;
    }
    public double getPrixKilo() {
        return prixKilo;
    }
    public static void setPrixKilo(double prixKilo) {
        Canard.prixKilo = prixKilo;
    }    
}
