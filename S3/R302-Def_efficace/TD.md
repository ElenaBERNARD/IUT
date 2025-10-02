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
# TD 2

## Les collections

**Set :** ensemble d'objet non indidces sans doublons
**List :** ensemble d'objets indicés
**Map :** ensemble associatifs d'objets non indices, chaque objet etant associe a une cle. Une cle est unique, mais plusieurs cles peuvent être associee au meme objet. 
**Queue :** ensemble d'objets, non indicé avec un schéma d'acces FIFO/LIFO

En Java les collections utilisent la généricité

En effet Set, List, Map, Queue sont des interfaces. Dans la plupart des cas on utilise une implémentation comme HashSet, ArrayList, HashMap, ArrayQueue

```
// Faux car int n'est pas une classe !
// Set<int> set = new HashSet<int>();

// Correct
Set<Integer> set = new HashSet<>();
```

**Methodes communes :**
```
int size()
void clear()
boolean isEmpty()
```
#### HashSet

boolean add(E e) :  ajoute l'objet de classe E 
boolean remove(Object o) : enleve o s'il existe
boolean contains(Object o)
Iterator\<E> iterator() : retourne un iterateur sur le set

#### ArrayList

boolean add(E e) : ajouter en fin de liste
void add(int index, E e) :  insertion en index
E set(int index)
int indexOf(Object o);

##### HashMap

v put(k key, v value) : ajoute/modifie un ensemble (cle, valeur). Si la cle existe deja alors l'ancienne est écrasée et remplacée.
v get(Object key) : renvoie l'objet associé a la clé si elle existe
boolean containsKey(Object key)
v remove(Object key) : supprice et retourne la valeur associé a la clé
Set\<K> keySet() : renvoie un set des clés

#### ArrayQueue
**File**
boolean offer(E e) : ajoute en fin de queue
E poll() : supprime et renvoie l'element en tete de queue

**Pile**
void push(E e) : ajoute en debut de queue
E pop() : supprime et renvoie l'element en tete de queue

#### Arbres
**Profondeur d'abord**
```
type_retour parcours(Node n){
    // traitement 1 sur n (eventuellment vide)
    pour chage fils f de n:
        type_retour val = parcours(f)
        //tests sur val (optionnel)
        si val == ...
            do
        sinon
            do
        fin si
    fin pour
    retourne valeur_defaut
}
```

```
class Node {
    public int value:
    public List<Node> children;

    Node(int value){
        children = new ArrayList<>();
        this.value=value;
    }
}
```
```
boolean contains(Node n, int val){
    if(n.val == val){
        return true;
    }
    for(Node fils : n.children){
        if(contains(fils, val)){
            return true;
        }
    }
    return false;
}
```
```
int treeDepth(Node n){
    if(n.children.isEmpty()){
        return 1
    }
    int max = 0;
    int depth;

    for(Node fils : n.children){
        depth = treeDepth(fils);
        if(max > depth){
            max = depth;
        }
    }
    return max + 1;
}
```
**Largeur d'abord**
```
public boolean contains(Node n, int val){
    if(n.val == val) return true;
    Queue<Node> queue = new ArrayDeque<>();
    for(Node fils : n.children)
        queue.offer(fils);
    while(!queue.isEmpty()){
        if(p.val == val)
            return true;
        else{
            for(Node fils : n.children)
                queue.offer(fils);
        }
    }
}
```

**Largeur maximal arbre**
```
public int maxWidth(Node n){
    int max = 0;
    int width = 0;
    level = 0;
    while((width = nbNodeByLevel(n, 0, level)) > 0){
        if(width > max)
            max = width;
        kevel++;
    }
}

public int nbNodeByLevel(Node n, int curLevel, int searchLevel){
    if(curLevel >= searchLevel){
        return 1;
    }
    int nb = 0;
    for(Node fils: n.children)
        nb += nbNodesByLevel(f, curlevel++, searchLevel)
    return nb
}
```
```
public int maxWidth(Node n){
    int max = 0;
    List<Integer> nbNodesByLevel = new ArrayList<>();
    countByLevel(n, 1, nbNodesByLevel);
    return nbNodesByLevel.max();
}

public void countByLevel(Node n, int level, List<Integer> nbNodes){
    if(nbNodes.size() < level)
        nbNodes.add(1);
    else
        nbNodes.set(level, nbNodes.get(level) + 1)
    for(Node fils: n.children){
        countByLevel(fils, level++, nbNodes);
    }
}
```

#### Arbre binaire ordonné
```
class Node {
    Node left;
    Node right
    int value;
    
    public Node(int value){
        this.value = value;
        this.left = null;
        this.right = null;
    }
}

public int insert(int value){
    insertRecur(root, value);
}

private void insertRecur(Node n, int value){
    if(n == null)
        root = new Node(value);
    else if(value <= n.value){
        if(n.left != null)
            insertRecur(n.left, value);
        else
            n = new Node(value);
    }
    else{
        if(n.right != null)
            insertRecur(n.right, value);
        else
            n = new Node(value);
    }
}

private void insertRecur(Node n, int value){
    if(n == null)
        n = new Node(value);
    else if(value <= n.value)
        insertRecur(n.left, value);
    else
        insertRecur(n.right, value);
}