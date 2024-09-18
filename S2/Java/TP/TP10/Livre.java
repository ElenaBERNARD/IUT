public class Livre extends Document {
    protected String auteur;
    protected int nbPages;
    
    public Livre(){
        super();
    }

    public Livre(String titre, String auteur, int nbPages){
        super(titre);
        this.auteur = auteur;
        this.nbPages = nbPages;
    }

    public Livre(Livre l){
        this(l.titre, l.auteur, l.nbPages);
    }

    public String toString(){
        return super.toString() + 
        "\nAuteur : " + this.auteur + 
        "\nPages : " + this.nbPages;
    }

    public boolean equals(Livre l){
        return this.numEnregistrement == l.numEnregistrement 
        && this.titre.equals(l.titre)
        && this.auteur.equals(l.auteur)
        && this.nbPages == l.nbPages;
    }
}
