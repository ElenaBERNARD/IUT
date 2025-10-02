import java.util.Scanner;
public class GoPersonne {
    public static void main(String[] args) {
        //On creer deux personnes
        Personne p1 = new Personne("BERNARD", "Elena", 23, 02, 2005, 'F', false);
        Personne p2 = new Personne("RANDOM", "Random", 01, 02, 2003, 'F', false);

        //On affiche
        System.out.println(p1);
        System.out.println(p2);

        //On marie p1 et p2
        p1.marierA(p2);

        //On affiche
        System.out.println(p1);
        System.out.println(p2);

        //On verifie le mariage
        System.out.println("Verif est marie : " + p1.getEstMarie());

        //On divorce
        p1.divorcer();

        //On affiche
        System.out.println(p1);
        System.out.println(p2);

        //On re-marie
        p1.marierA(p2);

        //On creer une nouvelle personne
        System.out.println(p2.enfanter("Valentn", 'B'));

        System.out.println();

        //On creer deux comptes
        Compte c1 = new Compte();
        Compte c2 = new Compte();

        //On ajoute 1000 sur le compte 1
        c1.setSolde(1000);

        //On affiche
        System.out.println(c1);
        System.out.println(c2);

        //On depose 200 du compte c1 sur le compte c2
        c2.deposer(c1.retirer(200));

        //On affiche
        System.out.println(c1);
        System.out.println(c2);

        //p1 et p2 ont le meme compte en banque
        p1.setCompte(c1);
        p2.setCompte(c1);

        //On depose 150 sur le compte de p1
        p1.getCompte().deposer(150);

        //Cela modifie aussi celui de p2
        System.out.println(p2.getCompte());

        //On modifie le compte de p2
        p2.getCompte().retirer(300);

        //Cela modifi aussi celui de p1
        System.out.println(p1.getCompte());
    }
}
