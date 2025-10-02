public class Match {
    private Club c1;
    private Club c2;
    private int scoreLocaux;
    private int scoreVisiteurs;

    public Match(Club locaux, Club visiteurs){
        this.c1 = locaux;
        this.c2 = visiteurs;
        this.scoreLocaux = 0;
        this.scoreVisiteurs = 0;
    }

    public String toString(){
        return "\n" + this.c1.getNom() + " contre " + this.c2.getNom() +
        "\n" + this.scoreLocaux + " - " + this.scoreVisiteurs;
    }

    public void incrementerScoreLocaux(){
        this.scoreLocaux += 1;
    }

    public void incrmenterScoreVisiteurs(){
        this.scoreVisiteurs += 1;
    }

    public int getScoreLocaux(){
        return this.scoreLocaux;
    }

    public int getscoreVisiteurs(){
        return this.scoreVisiteurs;
    }
}
