import java.util.ArrayList;

public class TestFerme {
    public static void main(String[] args) {
        Canard.setPoidsAbatage(1.7);
        Poule.setPoidsAbatage(3.0);
        Canard.setPrixKilo(4.90);
        Poule.setPrixKilo(7.80);

        Canard c1 = new Canard(1.4, 1);
        Canard c2 = new Canard(2.1, 2);
        Poule p1 = new Poule(3.2, 3);
        Poule p2 = new Poule(3.5, 4);

        ArrayList<Volaille> v = new ArrayList<Volaille>();

        v.add(c1);
        v.add(c2);
        v.add(p1);
        v.add(p2);

        Voliere voliere = new Voliere(v);

        System.out.println(voliere);
        System.out.println("Prix total volailles : " + voliere.prixVolaillesTotal());


        System.out.println("\n\nVolaille abatable :");
        voliere.afficheAbatable();
        System.out.println("Prix volailles abatables : " + voliere.prixVolaillesAbatables());

    }
}