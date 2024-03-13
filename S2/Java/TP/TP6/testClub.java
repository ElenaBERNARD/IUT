public class testClub {
    public static void main(String[] args) {
        Club c1 = new Club();
        c1.init();

        Club c2 = new Club(c1);

        System.out.println(c1 + "\n");
        System.out.println(c2 + "\n");
    }
}