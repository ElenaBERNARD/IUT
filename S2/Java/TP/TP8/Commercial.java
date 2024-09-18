public class Commercial extends Employe {
    protected int nbVentes;

    public Commercial(){
    }

    public Commercial(String matricule, String nom, double indiceSalarial, int nbVentes){
        super(matricule, nom, indiceSalarial);
        this.nbVentes = nbVentes;
    }

    public Commercial(Commercial c){
        this(c.matricule, c.nom, c.indiceSalarial, c.nbVentes);
    }

    public String toString(){
        return super.toString() +
        "NbVentes : " + nbVentes;
    }

    @Override
    public double salaire(){
        return valeurSalarial * (1 + nbVentes/10);
    }


}
