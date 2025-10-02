## 1. Principes et utilité

* Une classe de type interface est un classe qui ne contient aucun attribut ni aucun code (sauf exception).
Elle ne contient donc que des déclarations de méthodes

* On ne dit pas que l'on hérite d'une interface mais qu'on l'implémente

* En apparence, une interface est une coquille vide qui ressemble a peu pres a unce classe abstraite qui ne contiendrait que des méthodes abstraites, exepte qu'une interface n'a pas de constructeur. On ne peut pas faure un new sur une interface

**Remarque :** le concept d'interface n'existe pas en C++

En pratique, les intefaces permettent de définir des fonctionnatlités génériques qui peuvent donc s'appliquer a différents types d'objets.
Elles sont également utilisée pour définir des expressions lambda

**Exemple :** pour représenter des éléments triables, on va utiliser une interface :

```
interface Triable {
    public int comparer(Triable t);
    // Renvoie 0 si this == t
    // Renvoie < 0 si this < t
    // Renvoie > 0 si this > t
}
```

On peut ensuite implémenter une méthodes qui trie des Triables.
En effet, le compilateur peut parfaitement manipuler des variables de ce type et lors de l'execution, si on utilise des instances de classes qui implémentent Triable. Cela correspond à un héritage et donc utilise le *polymorphisme*. La JVM appellera donc automatiquement les méthode comparer() de ces instances.

**Exemple :**
```
public void triBulle(List\<Triable\> list){
    boolean swap;
    int len = list.size();
    Triable t1, t2;
    
    swap = true;
    while(swap){
        swap = false;
        for(int i = 0; i < len-1; i++){
            t1 = list.get(i);
            t2 = list.get(i+1);
            if(t1.comparer(t2) > 0){
                swap = true;
                list.set(i, t2);
                list.set(i+1, t1);
            }
        }
        len -= 1;
    }
}
```

# II. Les interfaces fonctionnelles (IF)

Une interface fonctionnelle est une interface avec *une seule* méthode, ***mais*** en option des méthodes par défaut qui peuvent avoir du code. 

**Exemple :** (tiré de l'API Java)
```
interface Consumer\<T\> {
    void accept(T t);
    default Consumer and Then(Consumer\<T\> c){
        code
        ...
    }
}
```
**Remaques :** 
> Les classes qui implémentent une interface contenant des méthodes par défaut héritent du code de ces méthodes

> Avant Java 8 et l'introduction des IF, il existait déjà des interfaces fonctionnelles (cf Runnable) mais qui n'étaient pas nommées IF
Avec Java 8, de nombreuses IF ont été introduites afin de représenter les traitements les plus courants, classés en 4 types :

* Function : transformations faites sur un ou plusieurs parametres et qui revoient une valeur, ces IF déclarent des méthodes T applyXXX(...)

* Consumer : actions lancé sur un ou plusieurs paramètre, sans retour méthodes : void accept(...)

* Predicate : test basé sur un ou plusieurs paramètres qui renvoie une booléen

* Supplier : Pas de paramètres qui renvoie une objet, méthodes getX()

# III. Les fonctions flechées

**Cas 1 :** (A ne pas faire)

```
class Test IF{
    ...
    public void toto(){
        Consumer<Double> printer = val -> System.out.println(val);
        BiFunction<Integer, Integer, Double> div = (x, y) -> (double) x/y;

        // printer(1.0); Ne fonctionne pas !
        printer.accept(div.apply(3, 4)); // Affiche 0.75
    }
}
```

**Remaques :** 

>Le compilateur est capable de comprendre tout seul que l'expression lambda affectée à une IF est le code de la méthode déclarée dans l'IF. Il est également capable de vérifier si les paramètres de l'expression lambda sont du bon type

>On n'écrit presque jamais de code ou l'on doit appeler explicitement les méthodes des IF car généralement on les utilise dans le cas 2

Parfois, les calsses IF de l'API ne correspondent pas aux besoins applicatifs ou n'ont pas des noms très pertinents. Le développeur créer alors ses propres IF.

**Cas 2 :** On veut creer des IF pour representer l'acte de soigner un humain

```
public interface Doctor{
    void soigner(Humain h);
    default String examiner(Humain h){
        if(h.age > h.esperance de vie)
            return "mort";
        return "Vivant";
    }
}

class Population {
    List<Humain> pop;

    ...

    public void traiter(Doctor doc){
        for(Huamin h : pop){
            if(doc.examiner(h).equals("vivant"))
                doc.soigner(h);
        }
    }
}

class Life {
    Population pop = new Population();

    ...

    Doctor bad_doc = h -> h.esperanceVie -= 5;

    pop.traiter(bad_doc); // OK compil et exec

    pop.traiter(h -> { h.esperanceVie+= 1; h.poids -+ 1;});
}
```

# IV. Expressions lambda (EL)

## 1. Utilisation

**Remarque :** avant la V8, il était tout de même possible de définir le code d'une méthode d'interface "a la volée", grâce aux classes anonymes internes

**Exemple :** l'IF ActionListener dans Swing :
```
class MyFrame exptends JFrame {
    ...

    JButton but = new JButton("OK");
    but.addActionListener(new ActionListener(){
        void actionPerformed(ActionEvent e){
            System.out.println("click");
        }
    });
}
```

Grâce aux EL, on peut drastiquement réduire le code de cet exemple

```
class MyFrame exptends JFrame {
    ...

    JButton but = new JButton("OK");
    but.addActionListener(
        () -> System.out.println("click")
    )
}
```

## 2. Syntaxe

Par défaut, la syntaxe d'une EL est : 
```
(param1, param2, ...) -> {
    ...
}
```

Mais il existe plein de *raccourcis* :

* Si un seul paramètre : pas besoin de parenthèses
* Si une seule instruction, pas besoin d'accolades ni de return

**Exemple :**
```
() -> { System.out.println("hello"); } // Return void
(msg) -> { System.out.println(msg); }  // Return void
(msg) -> System.out.println(msg);      // Return void

(a, b) -> { 
    if(a > b)
        return a;
    return b;
} // Return a si a > b, sinon b
(a, b) -> a > b ? a : b; // Return a si a > b, sinon b

a -> -a
```

Il existe aussi une syntaxe appelée "par référence methode"
Si une IF est utilisée en paramètre d'une méthode et qu'il existe une méthode classe dont la signature correspond à celle de la méthode déclarée dans l'IF, alors on peut utiliser cette méthode  à la place d'une fonction fléchée

**Exemple :**
```
class Logger {
    void affiche(Consumer<String> printer, String msg){
        ...
        printer.accept(msg);
    }
}

...

logger l = new Logger();
l.affiche((msg) -> System.out.println(msg), "hello");
l.affiche(System.out::println, "hello");