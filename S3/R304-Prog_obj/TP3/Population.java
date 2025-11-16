import java.util.*;

class Population {

    List<Humain> pop;

    Population() {
        this.pop = new ArrayList<Humain>();
    }

    public void vider() {
        pop.clear();
    }

    public void addHumain(Humain h) {
        pop.addLast(h);
    }

    public Humain getHumain(int index) {
        return pop.get(index);
    }

    public Humain removeHumain(Humain h) {
        pop.remove(h);
        return h;
    }

    public Humain removeHumain(int index) {
        Humain h = pop.get(index);
        pop.remove(index);
        return h;
    }

    public int taille() {
        return this.pop.size();
    }

    public void sortAsc(){
        Collections.sort(pop);
    }

    public void shufflePop(){
        Collections.shuffle(pop);
    }

    public void vieillir() {
        int n = pop.size() - 1;
        for (int i = n; i >= 0; i--) {
            Humain h = pop.get(i);
            h.vieillir();
            if (h.isDead()) {
                removeHumain(h);
            }
        }
    }

    public void print() {
        System.out.println("Population de taille : " + taille());
        for (Humain h : pop) {
            h.print();
        }
    }
}