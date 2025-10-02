public class TestFootball {
    public static void main(String[] args) {
        Club c1 = new Club();
        c1.faireEquipe(0);
        System.out.println(c1);

        Club c2 = new Club();
        c2.faireEquipe(0);

        Match m = new Match(c1, c2);
        System.out.println(m);


    }
}
