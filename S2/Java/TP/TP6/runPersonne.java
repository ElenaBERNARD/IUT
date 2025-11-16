public class runPersonne {
    public static void main(String[] args) {
        Personne p1 = new Personne("Test", 2);
        Personne p2 = new Personne("tseT", 5);
        Personne p3 = new Personne(p1);

        System.out.println(p1.equals(p2));
        System.out.println(p1.equals(p3));

        System.out.println(p3);
    }
}
