public class Ordinateur {
    private Ecran ecran;
    private Processeur processeur;

    public Ordinateur(){
        this.ecran = new Ecran();
        this.processeur = new Processeur();
    }

    public Ordinateur(Ecran e, Processeur p){
        this.ecran = e;
        this.processeur = p;
    }

    public void init(){
        this.ecran = new Ecran();
        this.ecran.init();
        
        this.processeur = new Processeur();
        this.processeur.init();
    }

    public String toString(){
        return "Ecran :\n"+this.ecran + 
        "\nProcesseur :\n" + this.processeur;
    }

    public Ecran getEcran(){
        return this.ecran;
    } 

    public Processeur gProcesseur(){
        return this.processeur;
    }

    public void setEcran(Ecran e){
        this.ecran = e;
    }

    public void setProcesseur(Processeur p){
        this.processeur = p;
    }


}
