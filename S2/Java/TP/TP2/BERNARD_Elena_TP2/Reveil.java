public class Reveil {
    private int heureCourante;
    private int minuteCourante;
    private int secondeCourante;
    private int heureAlarme;
    private int minuteAlarme;
    private int secondeAlarme;
    private boolean alarme;

    public String toString() {
        return "Heure :  [" + this.heureCourante + " : " + this.minuteCourante + " : " + this.secondeCourante + "]\n" +
                "Alarme : [" + this.heureAlarme + " : " + this.minuteAlarme + " : " + this.secondeAlarme + "] ("
                + ((alarme) ? "ON)" : "OFF)");
    }

    public int getHeureCourante() {
        return this.heureCourante;
    }

    public int getMinuteCourante() {
        return this.minuteCourante;
    }

    public int getSecondeCourante() {
        return this.secondeCourante;
    }

    public void setHoraireCourante(int heure, int minute, int seconde) {
        if (heure >= 0 && heure < 24)
            this.heureCourante = heure;
        else
            this.heureCourante = 0;

        if (minute >= 0 && minute < 60)
            this.minuteCourante = minute;
        else
            this.minuteCourante = 0;

        if (seconde >= 0 && seconde < 60)
            this.secondeCourante = seconde;
        else
            this.secondeCourante = 0;
    }

    public void setHoraireAlarme(int heure, int minute, int seconde) {
        if (heure >= 0 && heure < 24)
            this.heureAlarme = heure;
        else
            this.heureAlarme = 0;

        if (minute >= 0 && minute < 60)
            this.minuteAlarme = minute;
        else
            this.minuteAlarme = 0;

        if (seconde >= 0 && seconde < 60)
            this.secondeAlarme = seconde;
        else
            this.secondeAlarme = 0;
    }

    public void allumerAlarme() {
        this.alarme = true;
    }

    public void eteindreAlarme() {
        this.alarme = false;
    }

    /* Fonction qui inverse l'etat actuel de l'alarme */
    public void inverserAlarme() {
        this.alarme = !this.alarme;
    }

    public void controlerAlarme() {
        if (alarme && this.heureAlarme == this.heureCourante 
            && this.minuteAlarme == this.minuteCourante
            && this.secondeAlarme == this.secondeCourante)
            System.out.println("L'alarme sonne");
        else if (!alarme)
            System.out.println("L'alarme est eteinte (OFF)");
        else
            System.out.println("L'alarme ne sonne pas");
    }

    public void differenceAvec(Reveil r){
        int diff = this.heureCourante*3600 - r.getHeureCourante()*3600 
            + this.minuteCourante*60 - r.getMinuteCourante()*60 
            + this.secondeCourante - r.getSecondeCourante();
        if(diff < 0) 
            System.out.println("Le reveil est en retard par rapport a l'autre de " + -diff + "s");
        else if(diff > 0) 
            System.out.println("Le reveil est en avance par rapport a l'autre de " + diff + "s");
        else
            System.out.println("Les reveils sont synchronise");
    }

    public boolean comparerA(Reveil r){
        if(this.heureCourante == r.getHeureCourante() 
            && this.minuteCourante == r.getMinuteCourante() 
            && this.secondeCourante == r.getSecondeCourante()){
            System.out.println("Les horaires sont les mêmes");
            return true;
        }
        System.out.println("Les horaires ne sont pas les mêmes");
        return false;
    }

    public void incrementer(){
        this.secondeCourante++;
        if(this.secondeCourante > 59){
            this.secondeCourante = 0;
            this.minuteCourante++;
            if(this.minuteCourante > 59){
                this.minuteCourante = 0;
                this.heureCourante++;
                if(this.heureCourante > 23){
                    this.heureCourante = 0;
                }
            }
        }
    }
}
