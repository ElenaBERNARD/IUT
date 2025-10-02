public class TestOrdinateur {
    public static void main(String[] args) {
        Ordinateur o1 = new Ordinateur();
        Ordinateur o2 = new Ordinateur();
        o2.init();

        System.out.println("Information des deux ordinateurs :\n");
        System.out.println("Ordinateur o1 :\n" +o1 + "\n");
        System.out.println("Ordinateur o2 :\n" +o2 + "\n");

        Processeur p = new Processeur("AMD", 60);

        o1.setProcesseur(p);

        System.out.println("Processeur AMD de frequence 60 a o1 :\n");
        System.out.println("Ordinateur o1 :\n" +o1 + "\n");

        Ecran temp = o1.getEcran();
        o1.setEcran(o2.getEcran());
        o2.setEcran(temp);

        System.out.println("Echange des ecran de o1 et o2 :\n");
        System.out.println("Ordinateur o1 :\n" +o1 + "\n");
        System.out.println("Ordinateur o2 :\n" +o2 + "\n");
    }
}
