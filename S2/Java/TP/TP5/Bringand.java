public class Bringand extends Humain {
    private String look;
    private int nbDameKidnappe;
    private int prime;
    private boolean estEmprisonne;

    public Bringand(){
        super();
        this.boisson = "tord-boyaux";
        this.look = "mechant";
        this.nbDameKidnappe = 0;
        this.prime = 100;
        this.estEmprisonne = false;
    }

    public Bringand(String nom, String boisson, String look, int nbDameKidnappe, int prime){
        super(nom, boisson);
        this.look = look;
        this.nbDameKidnappe = nbDameKidnappe;
        this.prime = 100;
        this.estEmprisonne = false;
    }

    public void kidnapper(Dame d){
        nbDameKidnappe++;
        prime += 50;
        d.seFaireKidnapper();
        parle("Ah ah ! " + d.getNom() + ", tu est mienne desormais !");
    }

    public void perdreDame(){
        this.nbDameKidnappe--;
        this.estEmprisonne = true;
    }

    public void sortirDePrison(){
        this.estEmprisonne = false;
    }

    @Override
    public String getNom(){
        return this.nom + " le " + this.look;
    }

    @Override
    public void sePresenter(){
        super.sePresenter();
        parle("Je suis " + this.look + " et j'ai kidnappe " + this.nbDameKidnappe + " dames !");
        parle("Ma tete est mise a prix " + this.prime + "$");
    }
}
