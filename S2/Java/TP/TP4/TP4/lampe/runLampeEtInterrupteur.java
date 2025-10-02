public class runLampeEtInterrupteur{
    public static void main(String[] args) {
        Lampe lampe1 = new Lampe();

        //On allume la lampe
        lampe1.changerInterrupteur();
        System.out.println(lampe1);
        System.out.println(lampe1.getInterrupteur() + "\n");

        //On eteint la lampe
        lampe1.changerInterrupteur();
        System.out.println(lampe1);
        System.out.println(lampe1.getInterrupteur());
    }
}