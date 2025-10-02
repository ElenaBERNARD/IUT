public class Lampe{
    private boolean estAllume;
    private Interrupteur interrupteur;

    public Lampe(){
        this.estAllume = false;
        this.interrupteur = new Interrupteur();
    }

    public String toString(){
        return (this.estAllume)? "La lampe est allume" : "La lampe est eteinte";
    }

    public void changerInterrupteur(){
        this.interrupteur.changerEtat();
        this.estAllume = !this.estAllume;
    }

    public Interrupteur getInterrupteur(){
        return this.interrupteur;
    }
}