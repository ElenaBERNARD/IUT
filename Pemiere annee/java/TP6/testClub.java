public class testClub {
    public static void main(String[] args) {
        //Creation du club c1 de facon interactive
        Club c1 = new Club();
        c1.init();

        //Creation du club c2 par copie du club c1
        Club c2 = new Club(c1);

        //Affichage des deux clubs a la suite
        System.out.println(c1 + "\n");
        System.out.println(c2 + "\n");
    }
}
