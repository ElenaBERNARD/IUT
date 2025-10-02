import java.util.ArrayList;
import java.util.Scanner;

public class Club {
    private String nom;
    private ArrayList<Personne> listeAdherents;

    //Ce constructeur ne fait rien
    public Club(){
    }

    //Ce constructeur copie les informations d'un club c vers ce club
    public Club(Club c){
        this.nom = new String(c.nom);
        this.listeAdherents = c.listeAdherents;
    }

    //Ce constructeur donne les valeurs de unNomClub et desAdherents a ce celub
    public Club(String unNomClub, ArrayList<Personne> desAdherents){
        this.nom = new String(unNomClub);
        this.listeAdherents = desAdherents;
    }

    //Cette fonction affiche les attributs de ce club
    public String toString(){
        String text = "\nNom du club : " + this.nom + "\n";
        for(int i = 0; i < this.listeAdherents.size(); i++){
            text += "\n--Adherent n°" + (i+1) + " :\n"+ this.listeAdherents.get(i);
        }
        return text;
    }

    //Cette fonction initialise interactivement ce club
    public void init(){
        Scanner sc = new Scanner(System.in);

        //Recherche du nom du club
        this.nom = "";
        System.out.print("Veuillez renseigner le nom du club :\n");
        while(this.nom.equals("")) this.nom = sc.nextLine();

        //Recherche du nombre d'adherents du club
        int n = 0;
        System.out.println("Entrer le nombre de membre du club : ");
        while(n < 1) n = sc.nextInt();

        this.listeAdherents = new ArrayList<Personne>();
        String nomAdherent = "";

        while(!nomAdherent.equals("STOP") && this.listeAdherents.size() < n){
            nomAdherent = "";
            //Pour savoir si l'adherent est salarie, etudiant, ou ni l'un ni l'autre
            System.out.println("\nL'adherent " + (this.listeAdherents.size()+1) + " est : 'salarie', 'edutiant', 'aucun' :\n(Entrez 'STOP' pour arreter la saisie)");
            while(!nomAdherent.equals("salarie") && !nomAdherent.equals("etudiant") && !nomAdherent.equals("aucun") && !nomAdherent.equals("STOP")) {
                nomAdherent = sc.nextLine();
            }

            //Entrer 'STOP' permet de couper la fonction avant d'avoir atteint le nombre de membres precedement renseigné
            if(!nomAdherent.equals("STOP")){
                Personne p;
                //Cas ou l'adherent est un salarie
                if(nomAdherent.equals("salarie")){
                    p = new Salarie();
                    p.init();
                }
                //Cas ou l'adherent est un etudiant
                else if(nomAdherent.equals("etudiant")){
                    p = new Etudiant();
                    p.init();
                }
                //Cas ou l'adherent n'est pas un salarie ou un etudiant
                else{
                    p = new Personne();
                    p.init();
                }
                this.listeAdherents.add(p);
            }
        }
    }

    //Cette fonction retourne la liste des adherents de ce club
    public ArrayList<Personne> getAdherents(){
        return this.listeAdherents;
    }

    //Cette fonction retourne le nom de ce club
    public String getNomClub(){
        return this.nom;
    }
} //fin classe Club
