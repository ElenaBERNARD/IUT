public class testFraction {
    public static void main(String[] args) {
        Fraction T1 = new Fraction(2, 3);
        Fraction T2 = new Fraction(T1);

        System.out.println(T1);
        System.out.println(T2);

        Fraction T3 = new Fraction();
        T3.init();

        System.out.println(T3);

        Fraction T4 = T1.inverse();

        System.out.println(T4);

        System.out.println(T4.ajoute(T1));

        Fraction T5 = T1.multiplie(T2);
        System.out.println(T5);

    }
}
