public class TestRecipient {
    public static void main(String[] args) {
        Recipient r1 = new Recipient();
        Recipient r2 = new Recipient();
        r1.setNom("R1");
        r2.setNom("R2");

        System.out.println(r1 + ",  " + r2);

        r2.remplir();
        r1.ajouter(5);
        System.out.println(r1 + ",  " + r2);

        while(!r1.estPlein()){
            r1.ajouter(r2.prelever(1));
        }
        System.out.println(r1 + ", " + r2);

        Recipient r3 = new Recipient("R3", 20);

        r3.ajouter(r2.prelever(2));
        r3.ajouter(r1.prelever(4));
        r2.ajouter(r3.prelever(1));

        System.out.println(r1 + ",  " + r2 + ", " + r3);
    }
}