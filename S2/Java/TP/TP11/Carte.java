public class Carte {
    private static final char valeurs[] = {'7', '8', '9', '0', 'V', 'D', 'R', 'A'};
    private static final char couleurs[] = {'P', 'C', 'K', 'T'};
    
    private int valeur;
    private int couleur;

    public Carte(){
    }

    public Carte(int valeur, int couleur) {
        this.valeur = valeur;
        this.couleur = couleur;
    }

    public String toString(){
        return "[" + valeurs[valeur] + "" + couleurs[couleur] + "]";
    }

    public int getValeur() {
        return valeur;
    }
    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
    
    public int getCouleur() {
        return couleur;
    }
    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    
}
