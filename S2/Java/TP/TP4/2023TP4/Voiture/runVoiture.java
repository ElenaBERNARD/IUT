import voiture.Voiture;

public class runVoiture{
    public static void main(String[] args) {
        Voiture v1 = new Voiture();

        //On affiche le voiture v1, par defaut au rapport 0
        System.out.println(v1);

        //On reduit le rapport pour mettre la 'marche arriere'
        v1.reduireRapport();
        System.out.println(v1);

        //On essaye de passer en dessous du rapport minimal
        v1.reduireRapport(2);

        //On essaye de passer en dessous du rapport minimal
        v1.reduireRapport();

        //On augmente le rapport
        v1.augmenterRapport(3);
        System.out.println(v1);

        //On essaie d'exceder la limite de 6 vitesses (par defaut)
        v1.augmenterRapport(6);
        System.out.println(v1);

        //On essaie d'exceder la limite de 6 vitesses (par defaut)
        v1.augmenterRapport();
    }
}