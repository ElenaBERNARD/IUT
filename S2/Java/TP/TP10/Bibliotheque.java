public class Bibliotheque {
    private String nomBibliotheque;
    private Document[] documents;

    private int prochainNumero;
    public static final int CAPACITE = 5;


    ////////// CONSTRUCTORS //////////
    public Bibliotheque(String nomBibliotheque){
        this.nomBibliotheque = nomBibliotheque;
        this.documents = new Document[CAPACITE];
        this.prochainNumero = 0;
    }

    public Bibliotheque(String nomBibliotheque, Document[] documents) {
        this.nomBibliotheque = nomBibliotheque;
        this.documents = documents;
        this.prochainNumero = 0;
    }

    ////////// TOSTRING //////////
    public String toString(){
        String res = "";
        for(int i = 0; i < CAPACITE; i++){
            if(this.documents[i] != null)
                res += "\n" + this.documents[i];
        }
        return res;
    }

    ////////// FUNCTIONS //////////
    public Document getDocument(int num) throws IllegalArgumentException {
        if (num >= CAPACITE || num < 0) {
            throw new IllegalArgumentException("Le numéro " + num + " est hors limite.");
        }
        else if (documents[num] == null){
            throw new IllegalArgumentException("Le document numéro " + num + " n'existe pas dans la bibliothèque.");
        }
        return documents[num];
    }


    public void ajouterDocument(Document d) throws Exception {
        boolean add = false;
        int idAdd = 0;
        for(int i = 0; i < CAPACITE; i++){
            if(!add && this.documents[i] == null){
                add = true;
                idAdd = i;
            }
            if(this.documents[i] != null && d.equals(this.documents[i])){
                throw new DocumentDejaPresent();
            }
        }
        if(add){
            this.documents[idAdd] = d;
            prochainNumero++;
        } else{
            throw new IllegalArgumentException("La bibliotheque est pleine");
        }
    }

    public boolean ajoute(Document d){
        boolean add = false;
        int idAdd = 0;
        for(int i = 0; i < CAPACITE; i++){
            if(!add && this.documents[i] == null){
                add = true;
                idAdd = i;
            }
            if(this.documents[i] != null && d.equals(this.documents[i])){
                add = false;
                break;
            }
        }
        if(add){
            this.documents[idAdd] = d;
        }
        return add;
    }


    public void retirerDocument(int num) throws IllegalArgumentException {
        if (num >= CAPACITE || num < 0) {
            throw new IllegalArgumentException("Le numéro " + num + " est hors limite.");
        }
        else if (documents[num] == null){
            throw new IllegalArgumentException("Le document numéro " + num + " n'existe pas dans la bibliothèque.");
        }
        this.documents[num] = null;
        System.out.println("Document retiré");
    }

    public boolean suprime(Document d){
        boolean del = false;
        for(int i = 0; i < CAPACITE; i++){
            if(d.equals(this.documents[i])){
                this.documents[i] = null;
                del = true;
                break;
            }
        }
        return del;
    }

    ////////// GETTERS //////////
    public String getNomBibliotheque() {
        return nomBibliotheque;
    }

    ////////// SETTERS //////////
    public void setNomBibliotheque(String nomBibliotheque) {
        this.nomBibliotheque = nomBibliotheque;
    }
}
