public class Match {
    private Club c1;
    private Club c2;
    private int scoreLocaux;
    private int scoreVisiteur;

    public Match(Club locaux, Club visiteurs){
        this.c1 = locaux;
        this.c2 = visiteurs;
        this.scoreLocaux = 0;
        this.scoreLocaux = 0;
    }

    public String toString(){
        return this.c1.getNom() + " -VS- " + this.c2.getNom() +
        "\n ____________________ \n" + 
        this.scoreLocaux + " - " + this.scoreVisiteur +
        "\n ____________________ \n";
    }
}
