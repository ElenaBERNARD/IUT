public class Interrupteur{
    private boolean estActive;

    public Interrupteur(){
        this.estActive = false;
    }

    public String toString(){
        return (this.estActive)? "L'interrupteur est active" : "L'interrupteur n'est pas active";
    }

    public void changerEtat(){
        this.estActive = !this.estActive;
    }
}