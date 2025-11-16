import java.util.Scanner;
public class GoPersonne {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Initialisation de la personne p1
        Personne p1 = new Personne();

        //Saisie du nom et du prenom
        System.out.println("Entrez un nom : ");
        p1.setNom(sc.nextLine());
        System.out.println("Entrez un prenom : ");
        p1.setPrenom(sc.nextLine());

        //Saisie de la date de naissance
        int j, m, a;
        System.out.println("Entrez un jour de naissance : ");
        j = sc.nextInt();
        System.out.println("Entrez un mois de naissance : ");
        m = sc.nextInt();
        System.out.println("Entrez une année de naissance : ");
        a = sc.nextInt();
        p1.setDateNaiss(j, m, a);
        sc.nextLine();
        
        //Affichage des attribut de p1
        System.out.println(p1.nom + " " + p1.prenom + ", " + p1.jourNaiss + "/" + p1.moisNaiss + "/" + p1.anneeNaiss);


        //Initialisation de la personne p2
        Personne p2 = new Personne();

        //Saisie du nom et du prenom
        System.out.println("Entrez un nom : ");
        p2.setNom(sc.nextLine());
        System.out.println("Entrez un prenom : ");
        p2.setPrenom(sc.nextLine());

        //Saisie de la date de naissance
        p2.setDateNaiss(p1.jourNaiss, p1.moisNaiss, p1.anneeNaiss);
        
        //Affichage des attribut de p2
        System.out.println(p2.nom + " " + p2.prenom + ", " + p2.jourNaiss + "/" + p2.moisNaiss + "/" + p2.anneeNaiss);

        //Affichage de p1 et p2
        System.out.println(p1);
        System.out.println(p2);
    }
}
