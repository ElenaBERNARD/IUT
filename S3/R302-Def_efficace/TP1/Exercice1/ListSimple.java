public class ListSimple {
    public Cell head;
    public int size;

    public ListSimple() {
        head = null;
        size = 0;
    }

    public Cell find(int value) {
        Cell c = head;
        while ((c != null) && (c.value != value)) {
            c = c.next;
        }
        return c;
    }

    public Cell get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Cell c = head;
        int i = 0;
        while ((c != null) && (index != i)) {
            c = c.next;
            i++;
        }
        return c;
    }

    public Cell append(int value) {
        Cell newCell = new Cell(value);
        if (size == 0) {
            head = newCell;
        } else {
            Cell c = get(size - 1);
            c.next = newCell;
        }
        size++;
        return newCell;
    }

    public Cell insert(int value, int index) {
        Cell c = null;
        Cell newCell = new Cell(value);
        if (index > size)
            index = size;
        else if (index < 0)
            index = 0;

        if (size == 0)
            head = newCell;
        else if (index == 0) {
            newCell.next = head;
            head = newCell;
        } else {
            c = get(index - 1);
            newCell.next = c.next;
            c.next = newCell;
        }
        size++;
        return newCell;
    }

    public Cell replace(int value, int index) {
        if (index < 0 || index >= size)
            return null;
        Cell c = get(index);
        c.value = value;
        return c;
    }

    public Cell removeAt(int index) {
        if (index < 0 || index >= size)
            return null;
        Cell c = null;
        if (index == 0) {
            c = head;
            head = head.next;
        } else {
            Cell prev = get(index - 1);
            c = prev.next;
            prev.next = c.next;
        }
        size--;
        return c;
    }

    public Cell remove(int value) {
        Cell c = head;
        Cell prev = null;
        while ((c != null) && (c.value != value)) {
            prev = c;
            c = c.next;
        }
        if (c == null)
            return null;
        if (prev == null)
            head = c.next;
        else
            prev.next = c.next;
        size--;
        return c;
    }

    public void print() {
        Cell c = head;
        System.out.print("[" + c.value);
        while (c.next != null) {
            c = c.next;
            System.out.print(", " + c.value);
        }
        System.out.println("]");
    }
}