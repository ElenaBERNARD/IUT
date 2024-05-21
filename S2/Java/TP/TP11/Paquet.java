import java.util.ArrayList;

public class Paquet {
    private ArrayList<Carte> cartes;
    
    public Paquet() {
    }

    @Override
    public String toString() {
        String s = "";
        for (Carte carte : cartes) {
            s += carte + "\n";
        }
        return s;
    }

    public Paquet(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    public ArrayList<Carte> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    
}
