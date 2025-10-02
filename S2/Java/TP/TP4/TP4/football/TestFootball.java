public class TestFootball {
    public static void main(String[] args) {
        //On creer interactivement un club c1
        Club c1 = new Club();
        //On cree une equipe dans le club c1
        c1.faireEquipe(0);
        //On affiche le club c1
        System.out.println(c1);

        //On creer interactivement un club c2
        Club c2 = new Club();
        //On cree une equipe dans le club c2
        c2.faireEquipe(0);
        //On affiche le club c2
        System.out.println(c2);

        //On creer un match m entre c1 et c2
        Match m = new Match(c1, c2);
        System.out.println(m);

        //On ajoute 1 au score de c1
        m.incrementerScoreLocaux();
        System.out.println(m);
    }
}
