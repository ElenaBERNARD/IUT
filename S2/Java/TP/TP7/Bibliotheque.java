public class Bibliotheque {
    private Document[] documents;

    public Bibliotheque(int capacite){
        this.documents = new Document[capacite];
    }

    public String toString(){
        String res = "";
        for(int i = 0; i < this.documents.length; i++){
            if(this.documents[i] != null)
                res += "\n" + this.documents[i];
        }
        return res;
    }

    public Document getDocument(int idx){
        if(idx >= this.documents.length) return null;
        return documents[idx];
    }

    public boolean ajoute(Document d){
        boolean add = false;
        int idAdd = 0;
        for(int i = 0; i < this.documents.length; i++){
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

    public boolean suprime(Document d){
        boolean del = false;
        for(int i = 0; i < this.documents.length; i++){
            if(d.equals(this.documents[i])){
                this.documents[i] = null;
                del = true;
                break;
            }
        }
        return del;
    }
}
