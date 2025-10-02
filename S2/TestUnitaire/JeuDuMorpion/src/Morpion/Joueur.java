package Morpion;

import java.util.List;

public class Joueur {
    public final String motif;

    public Joueur(String motif) {
        this.motif = motif;
    }

    public void joue(Grille grid) {
        List<Coordonnee> disponibles = grid.caseVides();
        grid.joue(this, disponibles.get((int) (Math.random()*disponibles.size())));
    }
}
