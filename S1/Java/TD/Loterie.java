public class Loterie {
    static final int N = 42;

    static public void show(int[] tab) {
        System.out.print("\033[0;96m[");
        for (int i = 0; i < tab.length - 1; i++) {
            System.out.print("\033[1;34m" + tab[i] + "\033[0;98m, ");
        }
        System.out.print("\033[1;34m" + tab[tab.length - 1] + "\033[0;96m]\u001B[0m\n");
    }

    static public void shuffle(int[] tab) {
        int randomIndex, temp;
        for (int i = N - 1; i >= 0; i--) {
            randomIndex = (int) (Math.random() * (i));
            temp = tab[i];
            tab[i] = tab[randomIndex];
            tab[randomIndex] = temp;
        }
    }

    public static void main(String[] args) {
        int[] shuffledTab = new int[N];

        for (int i = 1; i < N + 1; i++) {
            shuffledTab[i - 1] = i;
        }

        shuffle(shuffledTab);
        show(shuffledTab);
    }
}