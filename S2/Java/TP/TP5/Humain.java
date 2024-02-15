public class Humain {
    protected String nom;
    protected String boisson;

    public Humain(String nom, String boisson){
        this.nom = nom;
        this.boisson = boisson;
    }

    public Humain(){
        this("Hooman", "eau");    
    }

    public void parle(String texte){
        System.out.println(this.nom + " - " + texte);
    }

    public void sePresenter(){
        parle("Bonjour, je m'appel " + this.nom + " ! Ma boisson favorite est le/la/l'" + this.boisson);
    }

    public void boire(){
        parle("Ah ! un bon verre de " + this.boisson + " ! GLOUPS !");
    }

    public String getNom(){
        return this.nom;
    }

    public String getBoisson(){
        return this.boisson;
    }
}