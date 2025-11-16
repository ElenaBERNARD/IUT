public class Voiture {
    private int rapportCurrent;
    private int rapportMax;

    public Voiture(){
        this.rapportCurrent = 0;
        this.rapportMax = 6;
    }

    public Voiture(int rapportMax){
        this.rapportCurrent = 0;
        this.rapportMax = rapportMax;
    }

    public Voiture(int rapportCurrent, int rapportMax){
        this.rapportCurrent = rapportCurrent;
        this.rapportMax = rapportMax;
    }

    public String toString(){
        String s = "Vous etes au rapport " + this.rapportCurrent;
        if(this.rapportCurrent == 0){
            s = "Vous etes au point mort";
        }
        else if(this.rapportCurrent == -1){
            s = "Vous etes en marche arriere";
        }
        return s;
    }

    public void augmenterRapport(){
        if(this.rapportCurrent != this.rapportMax){
            this.rapportCurrent += 1;
        }
    }

    public void augmenterRapport(int n){
        this.rapportCurrent += n;
        if(this.rapportCurrent > this.rapportMax){
            this.rapportCurrent = this.rapportMax;
        }
    }

    public void reduireRapport(){
        if(this.rapportCurrent != -1){
            this.rapportCurrent -= 1;
        }
    }

    public void reduireRapport(int n){
        this.rapportCurrent -= n;
        if(this.rapportCurrent < -1){
            this.rapportCurrent = -1;
        }
    }
    
    public int getRapport(){
        return this.rapportCurrent;
    }

    public void setRapportMax(int n){
        this.rapportMax = n;
    }
}