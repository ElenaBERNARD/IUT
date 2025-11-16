public class Personne{
    private String nom;
    private String prenom;
    private int jourNaiss;
    private int moisNaiss;
    private int anneeNaiss;
    private char sexe;
    private boolean estMarie;
    private Personne conjoint;
    private Compte compte;

    /// CONSTRUCTEURS

    /*
     * Initialise une personne sans attributs
     */
    public Personne(){
        this("", "", 1, 1, 2000, 'X', false);
    }

    /*
     * Initialise une personne de nom nom, de prenom prenom, de date de naissance jourNaiss/moisNaiss/anneeNaiss, de sexe sexe, et estMarie
     */
    public Personne(String nom, String prenom, int jourNaiss, int moisNaiss, int anneeNaiss, char sexe, boolean estMarie){
        this.nom = nom;
        this.prenom = prenom;
        this.jourNaiss = jourNaiss;
        this.moisNaiss = moisNaiss;
        this.anneeNaiss = anneeNaiss;
        this.sexe = sexe;
        this.estMarie = estMarie;
    }

    /*
     * Initialise une personne de nom nom, de prenom prenom, de date de naissance jourNaiss/moisNaiss/anneeNaiss, de sexe sexe, estMarie, et de conjoint conjoint
     */
    public Personne(String nom, String prenom, int jourNaiss, int moisNaiss, int anneeNaiss, char sexe, boolean estMarie, Personne conjoint){
        this(nom, prenom, jourNaiss, moisNaiss, anneeNaiss, sexe, estMarie);
        this.conjoint = conjoint; 
    }

    /*
     * Initialise une personne de nom nom, de prenom prenom, de date de naissance jourNaiss/moisNaiss/anneeNaiss, de sexe sexe
     * et la marie a pretendant si possible
     */
    public Personne(String nom, String prenom, int jourNaiss, int moisNaiss, int anneeNaiss, char sexe, Personne pretendant){
        this(nom, prenom, jourNaiss, moisNaiss, anneeNaiss, sexe, false);
        if(!pretendant.getEstMarie() && pretendant.ageEn2024() > 18 && this.ageEn2024() > 18){
            this.conjoint = pretendant;
            pretendant.setConjoint(this);
            this.estMarie = true;
            pretendant.setEstMarie(true);
        }
    }


    /// AFFICHAGE

    /*
     * Affiche les attributs de la personne 
     */
    public String toString(){
        return this.nom + " " + this.prenom + ", " 
            + sexe + ((estMarie) ? ", Conjoint : " + this.conjoint.getNom() + " " + this.conjoint.getPrenom() : ", Non marie(e)") 
            + " (" + this.jourNaiss + "/" + this.moisNaiss + "/" + this.anneeNaiss + ")";
    }


    /// SETTERS 

    /*
     * Valorise le prenom a n_prenom
     */
    public void setPrenom(String n_prenom){
        this.prenom = n_prenom;
    }

    /*
     * Valorise le prenom a n_nom
     */
    public void setNom(String n_nom){
        this.nom = n_nom;
    }

    /*
     * Valorise sexe a c
     */
    public void setSexe(char c){
        this.sexe = c;
    }

    /*
     * Valorise estMarie a b
     */
    public void setEstMarie(boolean b){
        this.estMarie = b;
    }

    /*
     * Valorise conjoint a p
     */
    public void setConjoint(Personne p){
        this.conjoint = p;
    }

    /*
     * Valorise jourNaiss a j, moisNaiss a m, anneeNaiss a a
     */
    public void setDateNaiss(int j, int m, int a){
        this.jourNaiss = j;
        this.moisNaiss = m;
        this.anneeNaiss = a;
    }

    /*
     * Valorise compte a c
     */
    public void setCompte(Compte c){
        this.compte = c;
    }


    /// GETTERS

    /*
     * Renvoie la valeur de prenom
     */
    public String getPrenom(){
        return this.prenom;
    }

    /*
     * Renvoie la valeur de nom
     */
    public String getNom(){
        return this.nom;
    }

    /*
     * Renvoie la valeur de sexe
     */
    public char getSexe(){
        return this.sexe;
    }

    /*
     * Renvoie la valeur de estMarie
     */
    public boolean getEstMarie(){
        return this.estMarie;
    }

    /*
     * Renvoie la valeur de conjoint
     */
    public Personne getConjoint(){
        return this.conjoint;
    }

    /*
     * Calcul l'age de la personne en 2024
     */
    public int ageEn2024(){
        return 2024 - this.anneeNaiss;
    }

    /*
     * Renvoie la valeur de compte
     */
    public Compte getCompte(){
        return this.compte;
    }


    /// FONCTIONS 

    /*
     * Marie la personne a p si elle et p ne sont pas marier, et si les deux sont majeurs
     */
    public boolean marierA(Personne p){
        if(!p.getEstMarie() && !this.estMarie && p.ageEn2024() > 18 && this.ageEn2024() > 18){
            this.estMarie = true;
            p.setEstMarie(true);
            this.conjoint = p;
            p.setConjoint(this);
            return true;
        }
        return false;
    }

    /*
     * Verifie si la personne est marie a p
     */
    public boolean verifEstMarie(Personne p){
        return this.conjoint.getNom().equals(p.conjoint.getNom());
    }

    /*
     * Permet le divorce
     */
    public void divorcer(){
        this.conjoint.setEstMarie(false);
        this.conjoint.setConjoint(null);
        this.estMarie = false;
        this.conjoint = null;
    }

    /*
     * Permet la creation d'une personne
     */
    public Personne enfanter(String prenom, char sexe){
        if(this.sexe == 'F')
            return new Personne(this.nom, prenom, 24, 01, 2024, sexe, false);
        return null;
    }
}