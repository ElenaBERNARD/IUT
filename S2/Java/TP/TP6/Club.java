import java.util.ArrayList;
import java.util.Scanner;

public class Club {
    private String nom;
    private ArrayList<Personne> listeAdherents;

    public Club(){
    }

    public Club(Club c){
        this.nom = c.nom;
        this.listeAdherents = c.listeAdherents;
    }

    public Club(String unNomClub, ArrayList<Personne> desAdherents){
        this.nom = unNomClub;
        this.listeAdherents = desAdherents;
    }

    public String toString(){
        String s = "\nNom du club : " + this.nom + "\n";
        for(int i = 1; i < this.listeAdherents.size() + 1; i++){
            s += "\n--Adherent n°" + i + " :\n"+ this.listeAdherents.get(i-1);
        }
        return s;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);

        this.nom = "";
        System.out.print("Veuillez renseigner le nom du club :\n");
        while(this.nom.equals("")) this.nom = sc.nextLine();

        int n = 0;
        System.out.println("Entrer le nombre de membre du club : ");
        while(n < 1) n = sc.nextInt();

        this.listeAdherents = new ArrayList<Personne>();
        String nomAdherent = "";

        while(!nomAdherent.equals("STOP") && this.listeAdherents.size() < n){
            nomAdherent = "";
            System.out.println("\nL'adherent " + (this.listeAdherents.size()+1) + " est : 'salarie', 'edutiant', 'aucun' :\n(Entrez 'STOP' pour arreter la saisie)");
            while(!nomAdherent.equals("salarie") && !nomAdherent.equals("etudiant") && !nomAdherent.equals("aucun") && !nomAdherent.equals("STOP")) {
                nomAdherent = sc.nextLine();
            }
            if(!nomAdherent.equals("STOP")){
                Personne p;
                if(nomAdherent.equals("salarie")){
                    p = new Salarie();
                    p.init();
                }
                else if(nomAdherent.equals("etudiant")){
                    p = new Etudiant();
                    p.init();
                }
                else{
                    p = new Personne();
                    p.init();
                }
                this.listeAdherents.add(p);
            }
        }
    }

    public ArrayList<Personne> getAdherents(){
        return this.listeAdherents;
    }

    public String getNomClub(){
        return this.nom;
    }
}

