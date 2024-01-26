import  java.lang.Math;
import java.util.*;

public class RunReveil {
    public static void main(String[] args) {
        //Definition des deux reveils r1 et r2
        Reveil r1 = new Reveil();
        Reveil r2 = new Reveil();

        //Test de la fonction incrementer()
        r1.setHoraireCourante(23, 59, 59);
        System.out.println("Verification de incrementer() :\n" + r1);
        r1.incrementer();
        System.out.println("On incremente de 1s :\n" + r1 + "\n");

        //Test de la comparaison des reveils :
        //
        //Avec des horaires similaires :
        r1.setHoraireAlarme(20, 0, 0);
        r2.setHoraireAlarme(20, 0, 0);
        System.out.println("On compare les reveils :\n" + r1 + "\n" + r2);
        r1.comparerA(r2);
        r1.differenceAvec(r2);

        //Avec r1 en retard sur r2
        r2.incrementer();
        System.out.println("\nOn compare les reveils :\n" + r1 + "\n" + r2);
        r1.comparerA(r2);
        r1.differenceAvec(r2);

        //Avec r1 en avance sur r2
        r1.incrementer();
        r1.incrementer();
        System.out.println("\nOn compare les reveils :\n" + r1 + "\n" + r2);
        r1.comparerA(r2);
        r1.differenceAvec(r2);

        //Test de l'alarme avec alarme ON
        r1.allumerAlarme();
        r1.setHoraireAlarme(6, 30, 0);
        r1.setHoraireCourante(6, 28, 30);
        System.out.println("\nTest alarme avec alarme ON\n" + r1);
        for(int i = 0; i < 150; i++){
            r1.incrementer();
            r1.controlerAlarme();
        }
        System.out.println(r1);

        //Test de l'alarme avec alarme OFF
        r1.eteindreAlarme();
        r1.setHoraireCourante(6, 28, 30);
        System.out.println("\nTest alarme avec alarme OFF\n" + r1);
        for(int i = 0; i < 150; i++){
            r1.incrementer();
            r1.controlerAlarme();
        }
        System.out.println(r1);




    }
}
