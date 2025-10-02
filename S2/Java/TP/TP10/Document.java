public class Document {
    protected static int gidx = 1;
    protected int numEnregistrement;
    protected String titre;

    public Document(){
    }

    public Document(String titre){
        this.numEnregistrement = gidx++;
        this.titre = titre;
    }

    public Document(Document d){
        this(d.titre);
    }

    public String toString(){
        return "Numero : " + this.numEnregistrement +
        "\nTitre : " + this.titre;
    }

    public boolean equals(Document d){
        return this.numEnregistrement == d.numEnregistrement && this.titre.equals(d.titre);
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
