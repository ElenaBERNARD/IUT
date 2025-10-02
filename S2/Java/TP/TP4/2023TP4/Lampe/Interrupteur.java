public class Interrupteur{
    private boolean estEnfonce;

    public Interrupteur(){
        this.estEnfonce = false;
    }

    public String toString(){
        return (this.estEnfonce)? "L'interrupteur est enfonce" : "L'interrupteur n'est pas enfonce";
    }

    public void changerEtat(){
        this.estEnfonce = !this.estEnfonce;
    }
}