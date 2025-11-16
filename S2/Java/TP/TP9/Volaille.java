abstract class Volaille {
    protected double poids;
    protected int numIdentification;
    
    protected Volaille() {
    }

    protected Volaille(double poids, int numIdentification) {
        this.poids = poids;
        this.numIdentification = numIdentification;
    }

    @Override
    public String toString() {
        return "[poids=" + poids + ", id=" + numIdentification + "]";
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public int getNumIdentification() {
        return numIdentification;
    }

    public void setNumIdentification(int numIdentification) {
        this.numIdentification = numIdentification;
    }

    public abstract double getPrixKilo();
    public abstract double getPoidsAbatage();
}