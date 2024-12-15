import javax.swing.CellEditor;

public class ListDoubleCirc {
 
    public CellDouble head;
    public int size;
 
    public ListDoubleCirc() {
        head = null;
        size = 0;
    }
 
    public CellDouble find(int value) {
        if(head == null){
            return null;
        }
        CellDouble c = head;
        int i = 0;
        while(i <= size && c.value != value){
            c = c.next;
            i++;
        }
        if(i == size+1){
            return null;
        }
        return c;
    }
 
    public CellDouble get(int index) {
	    // pour éviter de tout parcourir, on peut tester si l'indice
        // est avant ou après la moitié de la liste. S'il est avant,
        // on explore la liste grâce à next, et sinon grâce à prev
        if(index < 0 || index >= size){
            return null;
        }
        CellDouble c = head;
        int i = 0;
        if(index < size / 2){
            while(index != i){
                c = c.next;
                i++;
            }
        }
        else{
            c = head.prev;
            i = size - 1;
            while(index != i){
                c = c.prev;
                i--;
            }
        }
        return c;
    }
 
    public CellDouble append(int value) {
        // pas besoin de trouver la dernière cellule : on y a accès
        // grâce à prev de la tête. Seul cas particulier, la liste est vide
        CellDouble newCell = new CellDouble(value);
        if(size == 0){
            head = newCell;
            head.next = head;
            head.prev = head;
        }
        else{
            newCell.prev = head.prev;
            newCell.next = head;
            head.prev.next = newCell;
            head.prev = newCell;
        }
        size++;
        return newCell;
    }
 
    public CellDouble prepend(int value) {
        // pas compliqué : insertion en tête = insertion en fin
        // puis déplacement de la tête
        append(value);
        head = head.prev;
        return head;
    }
 
 
    public CellDouble insert(int value, int index) {
        // on a get() pour trouver la cellule du point d'insertion
        // Seul cas particulier, la liste est vide
        if(index >= size){
            index = size - 1;
            CellDouble c = get(index);
            CellDouble newCell = new CellDouble(value);
            newCell.prev = c;
            newCell.next = head;
            c.next = newCell;
            head.prev = newCell;
            size++;
            return newCell;
        }
        else if(index < 0)
            index = 0;


        CellDouble newCell = new CellDouble(value);
        
        if(size == 0){
            head = newCell;
            head.next = head;
            head.prev = head;
        }
        else{
            CellDouble c = get(index);
            newCell.prev = c.prev;
            newCell.next = c;
            c.prev.next = newCell;
            c.prev = newCell;
            if(index == 0)
                head = newCell;
        }
        size++;
        return newCell;
    }
 
    public CellDouble replace(int value, int index) {
	    // utiliser get()
        if(index < 0 || index >= size)
            return null;
        CellDouble c = get(index);
        c.value = value;
        return c;
    }
 
    public CellDouble removeAt(int index) {
	    // utiliser get()
        if(index < 0 || index >= size)
            return null;
        CellDouble c = null;
        if(size == 1){
            c = head;
            head = null;
        }
        else{
            c = get(index);
            c.prev.next = c.next;
            c.next.prev = c.prev;
            if(index == 0)
                head = c.next;
        }
        size--;
        return c;
    }
 
    public CellDouble remove(int value) {
	    // utiliser find
        if(size < 1)
            return null;
        CellDouble c = find(value);
        if(c == null)
            return null;
        if(c == head)
            head = c.next;
        c.prev.next = c.next;
        c.next.prev = c.prev;
        size--;
        return c;
    }

    public void print() {
        CellDouble c = head;
        System.out.print("[" + c.value);
        while(c.next != head){
            c = c.next;
            System.out.print(", " + c.value);
        }
        System.out.println("]");
    }
}