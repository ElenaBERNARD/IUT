public class Roman extends Livre {
    protected String prixLitteraire;

    public Roman(){
        super();
    }

    public Roman(String titre, String auteur, int nbPages, String prixLitteraire){
        super(titre, auteur, nbPages);
        this.prixLitteraire = prixLitteraire;
    }

    public Roman(Roman r){
        this(r.titre, r.auteur, r.nbPages, r.prixLitteraire);
    }

    public String toString(){
        return super.toString() + 
        "\nPrix litt. : " + this.prixLitteraire; 
    }

    public boolean equals(Roman r){
        return this.numEnregistrement == r.numEnregistrement 
        && this.titre.equals(r.titre)
        && this.auteur.equals(r.auteur)
        && this.nbPages == r.nbPages
        && this.prixLitteraire.equals(r.prixLitteraire);
    }
}
