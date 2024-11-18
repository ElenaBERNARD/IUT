# TD 1
## Les listes

Implementation de listes chainées

**Classe Cell representant un maillon de la chaine**
```
public class Cell {
    public int value;
    public Cell next;

    public Cell(int value){
        this.value = value;
        next = null;
    }
}
```

**Classe list contenant la première cellule de la chaine**
```

public class List {
    public Cell head;
    public int size;

    public class List(){
        head = null;
        size = 0;
    }
    . . .
}
```

**Fonction de recherche d'un element dans List**
```
public Cell find(int value){
    Cell c = head;
    while((c != null) && (c.value != value)){
        c = c.next;
    }
    return c;
}
```

**Fonction de recuperation d'un element dans List**
```
public Cell get(int index){
    Cell c = head;
    int i = 0;
    while((c != null) && (index != i)){
        c = c.next;
        i++;
    }
    return c;
}
```

**Fonction d'ajout d'un element dans List**
```
public Cell append(int value){
    Cell newCell = new Cell(value);
    if(size == 0){
        head = newCell;
    }
    else {
        Cell c = get(size - 1);
        c.next = newCell;
    }
    size++;
    return newCell;
}
```

**Fonction d'insertion d'un element dans List**
```
public Cell insert(int value, int index){
    Cell c = null;
    Cell newCell = new Cell(value);
    if(index > size) 
        index = size;
    if(size == 0)
        head = newCell;
    else if(index == 0){
        newCell.next = head;
        head = newCell;
    }
    else{
        c = get(index - 1);
        newCell.next = c.next;
        c.next = newCell;
    }
    size++;
    return newCell;
}
```

**Fonction de remplacement d'un element dans List**
```
public Cell replace(int value, int index){
    if(index < 0 || index >= size)
        return null;
    Cell c = get(index);
    c.value = value;
    return c;
}
```