public class Dame extends Humain {
    private String couleurRobe;
    private boolean estLibre;

    public Dame(){
        super();
        this.boisson = "lait";
        this.couleurRobe = "blanche";
        this.estLibre = true;
    }

    public Dame(String nom, String boisson, String couleurRobe){
        super(nom, boisson);
        this.couleurRobe = couleurRobe;
        this.estLibre = true;
    }

    public void seFaireKidnapper(){
        this.estLibre = false;
        parle("AU SECOUUUUUUUUUUURS");
    }

    public void etreLibere(){
        this.estLibre = true;
    }

    public void changeRobe(String couleurRobe){
        this.couleurRobe = couleurRobe;
        parle("Regardez ma nouvelle robe " + this.couleurRobe + " !");
    }

    public boolean getEstKidnappee(){
        return !this.estLibre;
    }

    @Override
    public String getNom(){
        return "Miss " + this.nom;
    }

    @Override
    public void sePresenter(){
        super.sePresenter();
        parle("Ma robe est " + this.couleurRobe + ", c'est l'une de mes preferees !");
    }
}
