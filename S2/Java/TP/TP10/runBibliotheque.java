public class runBibliotheque {
    public static void main(String[] args) {
        Bibliotheque b = new Bibliotheque("Alexandrie");
        
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

        try{
        System.out.println(b.getDocument(5));
        }
        catch(
            IllegalArgumentException e){
                System.out.println(e);
        }

        try{
            b.retirerDocument(2);
        } catch(IllegalArgumentException e){
            System.out.println(e);
        }

        try{
            b.retirerDocument(-1);
        } catch(IllegalArgumentException e){
            System.out.println(e);
        }

        try{
            b.ajouterDocument(l);
        } catch(Exception e){
            System.out.println(e);
        }

        try{
            b.ajouterDocument(new Document("Doc1"));
            b.ajouterDocument(new Document("Doc2"));
            b.ajouterDocument(new Document("Doc4"));
            b.ajouterDocument(new Document("Doc5"));
            b.ajouterDocument(new Document("Doc6"));
        } catch(Exception e){
            System.out.println(e);
        }
    }
}
