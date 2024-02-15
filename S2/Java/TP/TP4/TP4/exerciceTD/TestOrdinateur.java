public class TestOrdinateur {
    public static void main(String[] args) {
        /*
         * tester les 2 constructeurs en créant 
         * 2 ordinateurs o1 et o2 
         * (o1 sera construite par défaut
         * et initialisée interactivement)
         */
        Ordinateur o1 = new Ordinateur();
        Ordinateur o2 = new Ordinateur();
        o1.init();

        /*
         * afficher les caractéristiques des deux instances
         */
        System.out.println("Information des deux ordinateurs :\n");
        System.out.println("Ordinateur o1 :\n" +o1 + "\n");
        System.out.println("Ordinateur o2 :\n" +o2 + "\n");

        /*
         * remplacer le processeur de o1 par un autre processeur
         */
        Processeur p = new Processeur("AutrePocesseur", 1234);
        o1.setProcesseur(p);

        /*
         * afficher  de  nouveau  les  caractéristiques  de  o1  
         * pour  vérifier  le  bon  remplacement  de  son processeur
         */
        System.out.println("Ordinateur o1 :\n" +o1 + "\n");

        /*
         * échanger les deux écrans des deux ordinateurs o1 et o2
         */
        Ecran temp = o1.getEcran();
        o1.setEcran(o2.getEcran());
        o2.setEcran(temp);

        /*
         * afficher  de  nouveau  les  caractéristiques  des  deux  
         * ordinateurs  o1  et  o2  pour  vérifier l'échange.
         */
        System.out.println("Echange des ecran de o1 et o2 :\n");
        System.out.println("Ordinateur o1 :\n" + o1 + "\n");
        System.out.println("Ordinateur o2 :\n" + o2 + "\n");
    }
}
