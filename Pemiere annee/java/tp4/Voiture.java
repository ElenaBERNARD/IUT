public class Voiture {
    private int rapport;
    private int maxRapport;

    public Voiture(){
        this.rapport = 0;
        this.maxRapport = 6;
    }

    public String toString(){
        return (this.rapport == 0)? "Vous etes au point mort" : 
        (this.rapport == -1)? "Vous etes en marche arriere" : "Vous etes au rapport " + this.rapport;
    }

    public void augmenterRapport(){
        if(this.rapport != this.maxRapport){
            this.rapport += 1;
        }
        else{
            System.out.println("Vous êtes déjà au rapport maximal");
        }
        
    }
    public void augmenterRapport(int n){
        if(this.rapport + n > this.maxRapport){
            this.rapport = this.maxRapport;
            System.out.println("Vous ne pouvez pas depasser rapport maximal");
        }
        else{
            this.rapport += n;
        }
    }

    public void reduireRapport(){
        if(this.rapport != -1){
            this.rapport -= 1;
        }
        else{
            System.out.println("Vous êtes déjà au rapport minimal");
        }
    }
    public void reduireRapport(int n){
        if(this.rapport - n < -1){
            this.rapport = -1;
            System.out.println("Vous ne pouvez pas depasser rapport minimal");
        }
        else{
            this.rapport -= n;
        }
    }
    
    public int getRapport(){
        return this.rapport;
    }

    public void setMaxRpport(int n){
        this.maxRapport = n;
    }
}