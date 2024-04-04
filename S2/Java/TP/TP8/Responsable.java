package TP8;

import java.util.ArrayList;
import java.util.Scanner;

import TP6.Etudiant;
import TP6.Salarie;

public class Responsable extends Employe {
    protected ArrayList<Employe> subordonnes;

    public Responsable(){
    }

    public Responsable(String matricule, String nom, double indiceSalarial, ArrayList<Employe> subordonnes){
        super(matricule, nom, indiceSalarial);
        this.subordonnes = subordonnes;
    }

    public Responsable(Responsable r){
        this(r.matricule, r.nom, r.indiceSalarial, r.subordonnes);
    }

    public String toString(){
        String s = super.toString();
        for(int i = 1; i < this.subordonnes.size() + 1; i++){
            s += "\nInferieur " + i + " :\n"+ this.subordonnes.get(i-1);
        }
        return s;
    }

    public void init(){
        Scanner sc = new Scanner(System.in);
        super.init();

        System.out.println("Entrez un nombre de subordonnes : ");
        int n = sc.nextInt();
        sc.nextLine();
        this.subordonnes = new ArrayList<Employe>();
        for(int i = 0; i < n; i++){
            Employe e = new Employe();
            e.init();
            this.subordonnes.add(e);
        }

    }
}
