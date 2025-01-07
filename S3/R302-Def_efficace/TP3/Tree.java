import java.util.*;

class Tree {

    public Node root;
    public int nbNodes; // pas nécessaire mais utile

    public Tree() {
        root = null;
        nbNodes = 0;
    }

    public Node addNode(int value, Node parent) {
        /*
         * 
         * - si parent est null :
         * - créer un nouveau noeud contenant value
         * - si root existe déja : ajouter root comme fils du nouveau noeud
         * - root devient le nouveau noeud
         * - incrémenter le nombre de noeud
         * - renvoyer le nouveau noeud
         * - sinon si l'arbre contient parent
         * - ajouter un noeud fils à parent, contenant value
         * - incrémenter le nombre de noeud
         * - renvoyer le nouveau noeud
         * - sinon renvoyer null
         */
        if (parent == null) {
            Node n = new Node(value);
            if (root != null) {
                n.addChild(root);
            }
            root = n;
            nbNodes++;
            return n;
        } else if (contains(parent, root) != null) {
            Node n = new Node(value);
            parent.addChild(n);
            nbNodes++;
            return n;
        } else {
            return null;
        }
    }

    public Node contains(Node toSearch, Node parent) {
        // NB : la recherche doit se faire en profondeur d'abord
        if (parent == toSearch) {
            return parent;
        }
        for (Node n : parent.children) {
            Node result = contains(toSearch, n);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public Node searchValueByLevel(int value, Node parent) {
        // NB : la recherche doit se faire en largeur d'abord */
        if (parent.value == value) {
            return parent;
        }

        Queue<Node> q = new LinkedList<Node>();
        for (Node fils : parent.children)
            q.offer(fils);
        while (!q.isEmpty()) {
            Node n = q.poll();
            if (n.value == value)
                return n;
            else {
                for (Node fils : n.children)
                    q.offer(fils);
            }
        }
        return parent;
    }

    public Node searchValueByDepth(int value, Node parent) {

        // NB : la recherche doit se faire en profondeur d'abord */
        if (parent.value == value) {
            return parent;
        }
        Node found;
        for (Node n : parent.children) {
            found = searchValueByDepth(value, n);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public Node searchValue(int value, int type) {
        Node n = null;
        if (type == 1)
            n = searchValueByDepth(value, root);
        else if (type == 2)
            n = searchValueByLevel(value, root);
        return n;
    }

    public void print() {
        if (root != null) {
            printNode(root, 0);
        }
    }

    public void printLevel() {
        if(root == null)
            return;
        
        // Affiche en largeur d'abord
        int currentLevelLength = 0, level = 0;
        Node currentNode;
        Queue<Node> queue = new LinkedList<Node>();

        // Ajout des fils de la racine
        for (Node fils : root.children)
            queue.offer(fils);
        // Affichage de la racine
        System.out.println(root.value);

        while (!queue.isEmpty()) {
            // Si la longueur du niveau courant est nulle, on passe au niveau suivant
            if (currentLevelLength == 0) {
                currentLevelLength = queue.size();
                level++;
            }

            // Récupération du noeud courant
            currentNode = queue.poll();

            // Affichage du noeud
            for (int i = 0; i < level * 2; i++)
                System.out.print(" ");
            System.out.println(currentNode.value);

            // Ajout des fils du noeud courant
            for (Node fils : currentNode.children)
                queue.offer(fils);

            // Décrémentation de la longueur du niveau courant
            currentLevelLength--;
        }
    }

    public void printNode(Node n, int level) {
        /*
         * - afficher 2*level espace puis la valeur contenue dans n
         * - pour chaque noeud fils de n :
         * - afficher le contenu du noeud, en incrémentant le niveau
         */
        for (int i = 0; i < level * 2; i++) {
            System.out.print(" ");
        }
        System.out.println(n.value);
        for (Node fils : n.children)
            printNode(fils, level + 1);
    }

}