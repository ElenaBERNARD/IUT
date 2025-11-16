import java.util.ArrayList;

public class Voliere {
    private ArrayList<Volaille> volailles;

    public Voliere() {
        this.volailles = new ArrayList<Volaille>();
    }

    public Voliere(ArrayList<Volaille> volailles) {
        this.volailles = volailles;
    }

    @Override
    public String toString() {
        String s = "Voliere :";
        for (Volaille volaille : volailles) {
            s += "\n" + volaille.toString();
        }
        return s;
    }

    public void affiche(){
        System.out.println(this);
    }

    public void afficheAbatable(){
        String s = "Voliere :";
        for (Volaille volaille : getAbatable()) {
            s += "\n" + volaille.toString();
        }
        System.out.println(s);
    }

    public ArrayList<Volaille> getVolailles() {
        return volailles;
    }

    public void setVolailles(ArrayList<Volaille> volailles) {
        this.volailles = volailles;
    }

    public void addVolaille(Volaille volaille){
        this.volailles.add(volaille);
    }

    public Volaille getVolailleById(int id){
        return this.volailles.get(id);
    }

    public ArrayList<Volaille> getAbatable(){
        ArrayList<Volaille> abatable = new ArrayList<Volaille>();
        for (Volaille volaille : this.volailles) {
            if(volaille.getPoids() >= volaille.getPoidsAbatage()){
                abatable.add(volaille);
            }
        }
        return abatable;
    }

    public double prixListeVolailles(ArrayList<Volaille> volailles){
        double sum = 0;
        for (Volaille volaille : volailles) {
            sum += volaille.getPoids()*volaille.getPrixKilo();
        }
        return sum;
    }

    public double prixVolaillesAbatables(){
        return prixListeVolailles(getAbatable());
    }

    public double prixVolaillesTotal(){
        return prixListeVolailles(this.volailles);
    }
}
