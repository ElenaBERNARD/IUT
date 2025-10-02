public class RunReveil {
    public static void main(String[] args) {
        Reveil r1 = new Reveil();
        Reveil r2 = new Reveil();

        r1.setHoraireCourante(0, 0, 0);
        System.out.println("Test a l'heure 0 : 0 : 0\n" + r1);
        r1.incrementer();
        System.out.println(r1);
        r1.setHoraireCourante(20,15,45);
        System.out.println("\nTest a l'heure 20 : 15 : 45\n" + r1);
        r1.incrementer();
        System.out.println(r1);
        r1.setHoraireCourante(20, 18,59);
        System.out.println("\nTest a l'heure 20 : 18 : 59\n" + r1);
        r1.incrementer();
        System.out.println(r1);
        r1.setHoraireCourante(21,59,59);
        System.out.println("\nTest a l'heure 21 : 59 : 59\n" + r1);
        r1.incrementer();
        System.out.println(r1);
        r1.setHoraireCourante(23,59,59);
        System.out.println("\nTest a l'heure 23 : 59 : 59\n" + r1);
        r1.incrementer();
        System.out.println(r1);

        r2.setHoraireCourante(0, 0, 1);

        System.out.println("\nTest avec le reveil en retard");
        System.out.println(r1);
        System.out.println(r2);
        r1.comparerA(r2);
        r1.differenceAvec(r2);

        System.out.println("\nTest avec le reveil a la meme heure");
        r1.incrementer();
        System.out.println(r1);
        System.out.println(r2);
        r1.comparerA(r2);
        r1.differenceAvec(r2);

        System.out.println("\nTest avec le reveil en avance");
        r1.incrementer();
        System.out.println(r1);
        System.out.println(r2);
        r1.comparerA(r2);
        r1.differenceAvec(r2);

        System.out.println("\nTest de l'alarme avec alarme ON");
        r1.allumerAlarme();
        r1.setHoraireAlarme(6, 30, 0);
        r1.setHoraireCourante(6, 29,50);
        for(int i = 0; i < 12; i++){
            System.out.println(r1);
            r1.controlerAlarme();
            r1.incrementer();
        }

        System.out.println("\nTest de l'alarme avec alarme OFF");
        r1.eteindreAlarme();
        r1.setHoraireAlarme(6, 30, 0);
        r1.setHoraireCourante(6, 29,50);
        for(int i = 0; i < 12; i++){
            System.out.println(r1);
            r1.controlerAlarme();
            r1.incrementer();
        }
    }   
}