import java.util.ArrayList;

public class runEntrerpise {
    public static void main(String[] args) {
        Employe e1 = new Employe();
        Employe e2 = new Employe();
        Employe e3 = new Employe();
        e1.init();
        e2.init();
        e3.init();

        ArrayList<Employe> subordonnes = new ArrayList<Employe>();
        subordonnes.add(e1);
        subordonnes.add(e2);
        subordonnes.add(e3);

        Entreprise e = new Entreprise(subordonnes);

        e.afficheLexicographique();

        e.afficheSalaire();
    }
}
