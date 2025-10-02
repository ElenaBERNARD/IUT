public class Cowboy extends Humain {
    private String adjectif;
    private int popularite;

    public Cowboy(){
        super();
        this.boisson = "whisky";
        this.adjectif = "vaillant";
        this.popularite = 0;
    }

    public Cowboy(String nom, String boisson, String adjectif, int popularite){
        super(nom, boisson);
        this.adjectif = adjectif;
        this.popularite = popularite;
    }

    public void liberer(Dame d, Bringand b){
        if(d.getEstKidnappee()){
            b.perdreDame();
            d.etreLibere();
            parle("Vous voila libre " + d.getNom() + " !");
            parle(b.getNom() + " suivez moi, vous allez pourrir quelques temps en prison !");
            this.popularite++;
        }
    }

    public void tirer(Bringand b){
        System.out.println("Le " + this.adjectif + " " + this.nom + " tire sur " + b.getNom() + ". PAN !");
        parle("Prend ca, rascal !");
    }

    @Override
    public void sePresenter(){
        super.sePresenter();
        parle("Les gens disent que je suis " + adjectif + ", mais je ne fais que mon devoir !");
        parle("J'ai deja sauve " + popularite + " dames, je suis populaire !");
    }
}
