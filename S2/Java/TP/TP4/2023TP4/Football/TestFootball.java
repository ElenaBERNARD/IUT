public class TestFootball {
    public static void main(String[] args) {
        //On creer interactivement un club c1, puis on l'affiche
        Club c1 = new Club();
        c1.faireEquipe(0);
        System.out.println(c1);

        //On creer interactivement un club c2, puis on l'affiche
        Club c2 = new Club();
        c2.faireEquipe(0);
        System.out.println(c2);

        //On creer un match m entre c1 et c2, et on affiche ses informations
        Match m = new Match(c1, c2);
        System.out.println(m);

        //On ajoute 1 au score de c1
        m.incrementerScoreLocaux();
        System.out.println(m);
    }
}
