public class DocumentDejaPresent extends Exception {
    private Document document;

    public String toString(){
        return "Le document" + document.getTitre() +  "est deja present dans le bibliotheque";
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Document getDocument(){
        return this.document;
    }
}
