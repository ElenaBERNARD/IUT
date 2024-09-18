import java.util.ArrayList;

public class Entreprise {
    private ArrayList<Employe> salaries;

    public Entreprise(){
    }

    public Entreprise(ArrayList<Employe> salaries){
        this.salaries = salaries;
    }

    public Entreprise(Entreprise e){
        this(e.salaries);
    }

    public void afficheLexicographique(){
        int indiceMin;
        for(int i = 0; i < this.salaries.size(); i++){
            indiceMin = i;
            for(int j = i+1; j < this.salaries.size(); j++){
                if(this.salaries.get(indiceMin).getNom().compareToIgnoreCase(this.salaries.get(j).getNom()) > 0){
                    indiceMin = j;
                }
            }
            System.out.println(this.salaries.get(indiceMin));
            Employe buffer = this.salaries.get(indiceMin);
            this.salaries.set(indiceMin, this.salaries.get(i));
            this.salaries.set(i, buffer);
        }
    }

    public void afficheSalaire(){
        int indiceMin;
        for(int i = 0; i < this.salaries.size(); i++){
            indiceMin = i;
            for(int j = i+1; j < this.salaries.size(); j++){
                if(this.salaries.get(indiceMin).salaire() > this.salaries.get(j).salaire()){
                    indiceMin = j;
                }
            }
            System.out.println(this.salaries.get(indiceMin));
            Employe buffer = this.salaries.get(indiceMin);
            this.salaries.set(indiceMin, this.salaries.get(i));
            this.salaries.set(i, buffer);
        }
    }

    public void ajoute(Employe e){
        this.salaries.add(e);
    }

    public void remplacer(Employe remplace, Employe remplacant){
        this.salaries.set(this.salaries.indexOf(remplace), remplacant);
    }

    public int chercher(Employe e){
        return this.salaries.indexOf(e); 
    }
}
