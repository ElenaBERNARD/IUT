public class Garcon extends Homme {
    public Garcon(String nom){
        super(nom);
    }

    public Garcon(int age, int poids, String nom, int batifolage){
        this.age = age;
        this.poids = poids;
        this.nom = nom;
        this.batifolage = batifolage;
    }
}
