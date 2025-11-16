public class Femme extends Humain {
 
    private int fertilite;
 
    Femme(String nom) {
	    super(nom);
        this.fertilite = 0;
        setEsperanceVie();
    }
 
    Femme(int age, int poids, String nom, int fertilite) {
	    super(age, poids, nom);
        this.fertilite = fertilite;
        setEsperanceVie();
    }
 
    public int getFertilite() {
	    return this.fertilite;
    }
 
    public void vieillir() {
        age++;
        if(this.age == 15){
            this.fertilite = loto.nextInt(100);
        }
        if (age <= 20) poids = 3+(int)(2.6*age);
        else if (age >= 50) poids += (age % 2);
    }

    public boolean peutProcreer(){
        return this.age > 15 && this.age < 50 && this.poids < 150;
    }
 
    public Humain rencontre (Homme h) {
	    if(peutProcreer() && h.peutProcreer()){
            int f = loto.nextInt(100);
            if(f > this.fertilite){
                return null;
            }
            int p = loto.nextInt(100);
            Humain bebe;
            if(p < 50){
                bebe = new Homme("(" + this.nom + "-" + h.nom + ")");
            }
            else{
                bebe = new Femme("(" + this.nom + "-" + h.nom + ")");
            }
            h.grossir(loto.nextInt(20));
            this.grossir(10);
            return bebe;
        }
        return null;
    }
 
    protected void setEsperanceVie() {
	    this.esperanceVie = loto.nextInt(40) + 55;
    }

    public boolean isHomme(){
        return false;
    }
    public boolean isFemme(){
        return true;
    }
}