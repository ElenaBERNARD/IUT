public class runBibliotheque {
    public static void main(String[] args) {
        Bibliotheque b = new Bibliotheque(4);
        
        Document d = new Document("Celeste");
        System.out.println(d);

        Livre l = new Livre("LeSeigneurDesAnneaux", "JM Charr", 250);
        System.out.println(l);

        Roman r = new Roman("LaOuChantentLesEcrevisses", "Delia Owens", 368, "Prix AudioLib");
        System.out.println(r);

        b.ajoute(d);
        b.ajoute(l);
        b.ajoute(r);

        System.out.println(b);

        b.ajoute(l);

        System.out.println(b);

        b.suprime(d);

        System.out.println(b);
    }
}
