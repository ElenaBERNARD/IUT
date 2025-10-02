public class RunVoiture {
    public static void main(String[] args) {
        Voiture v1 = new Voiture();

        // On affiche v1 au rapport 0
        System.out.println(v1);

        // On reduit le rapport, -1 correspond à la marche arriere 'marche arriere'
        v1.reduireRapport();
        System.out.println(v1);

        // On essaye de passer en dessous du rapport minimal
        v1.reduireRapport(2);

        // On essaye de passer en dessous du rapport minimal
        v1.reduireRapport();

        // On augmente le rapport
        v1.augmenterRapport(3);
        System.out.println(v1);

        // On essaie de passer au dessus du rapport maximal
        v1.augmenterRapport(6);
        System.out.println(v1);

        // On essaie de passer au dessus du rapport maximal
        v1.augmenterRapport();
    }
}
