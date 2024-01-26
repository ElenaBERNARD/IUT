/**
 * Gestion d'un recipient
 */
public class Recipient {
    private double capacite;
    private double volume;
    private String nom;

    /**
     * Initialise un recipient de 10 litres de nom NoName
     */
    public Recipient() {
        this("NoName", 10);
    }

    /**
     * Initialise un recipient de capa litres et de nom nomR
     */
    public Recipient(String nomR, double capa) {
        this.nom = nomR;
        this.capacite = capa;
        this.volume = 0;
    }

    public String toString() {
        return this.nom + " : " + this.volume + "/" + this.capacite + "l";
    }

    /**
     * Renvoie la capacite du recipient
     */
    public double getCapacite() {
        return this.capacite;
    }

    /**
     * Renvoie le volume du recipient
     */
    public double getVolume() {
        return this.volume;
    }

    /**
     * Renvoie le nom du recipient
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Change la valeur de la capacite du recipient
     */
    public void setCapcite(double capa) {
        this.capacite = capa;
    }

    /**
     * Change la valeur du volume du recipient
     */
    public void setVolume(double vol) {
        this.volume = vol;
    }

    /**
     * Change la valeur du nom du recipient
     */
    public void setNom(String nomR) {
        this.nom = nomR;
    }

    /**
     * Verifie si un recipient est vide
     */
    public boolean estVide() {
        return this.volume == 0;
    }

    /**
     * Verifie si un recipient est plein
     */
    public boolean estPlein() {
        return this.volume == this.capacite;
    }

    /**
     * Preleve n litre du recipient
     * La valeur prelever est retournee
     */
    public double prelever(double n) {
        this.volume -= n;
        if (this.volume < 0) {
            n += this.volume;
            this.volume = 0;
        }
        return n;
    }

    /**
     * Ajoute n litre au recipient
     */
    public void ajouter(double n) {
        this.volume += n;
        if (this.volume > this.capacite)
            this.volume = this.capacite;
    }

    /**
     * Remplie le recipient au maximum de sa capacite
     */
    public void remplir() {
        this.volume = this.capacite;
    }

    /**
     * Vide le recipient
     */
    public void vider() {
        this.volume = 0;
    }
}