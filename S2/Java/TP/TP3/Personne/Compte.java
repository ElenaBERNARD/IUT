public class Compte {
    private double solde;

    public String toString(){
        return "Solde : " + this.solde;
    }

    public int getSolde(){
        return (int)this.solde;
    }

    public void setSolde(double s){
        this.solde = s;
    }

    public void deposer(double s){
        this.solde += s;
    }

    public double retirer(double s){
        this.solde -= s;
        if(this.solde < 0){
            s += this.solde;
            this.solde = 0;
        }
        return s;
    }
}