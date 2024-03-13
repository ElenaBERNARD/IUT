package Morpion;

import java.util.ArrayList;
import java.util.List;

public class Grille {

    private Joueur[] grid = new Joueur[9];

    public boolean estGagnee() {
        return (ligneGagnante(grid) || colonneGagnante(grid) || diagonaleGagnante(grid));
    }

    public boolean estPat() {
        for (Joueur joueur : grid) {
            if (joueur == null) return false;
        }
        return !estGagnee();
    }

    public List<Coordonnee> caseVides() {
        List<Coordonnee> placeLibres = new ArrayList<Coordonnee>();
        for (int i = 0; i < grid.length; i++) {
            if (grid[i] == null) {
                placeLibres.add(new Coordonnee(i / 3, i % 3));
            }
        }
        return placeLibres;
    }

    public void joue(Joueur joueur, Coordonnee coordonnee) {
        grid[coordonnee.ligne * 3 + coordonnee.colonne] = joueur;
    }

    public String gagnant() {
        if (ligneGagnante(0, grid) || colonneGagnante(0, grid)) return grid[0].motif;
        if (ligneGagnante(1, grid)) return grid[3].motif;
        if (ligneGagnante(2, grid)) return grid[6].motif;
        if (colonneGagnante(1, grid)) return grid[1].motif;
        if (colonneGagnante(2, grid)) return grid[2].motif;
        if (diagonaleGagnante(grid)) return grid[4].motif;
        return "";
    }

    public void display() {
        for (int li = 0; li < 3; li++) {
            for (int co = 0; co < 3; co++) {
                System.out.print(" " + (grid[li * 3 + co] == null ? "." : grid[li * 3 + co].motif));
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private static boolean ligneGagnante(Joueur[] grid) {
        for (int ligne = 0; ligne < 3; ligne++) {
            if (ligneGagnante(ligne, grid)) return true;
        }
        return false;
    }

    private static boolean ligneGagnante(int ligne, Joueur[] grid) {
        if (grid[ligne * 3] != null && grid[ligne * 3] == grid[ligne * 3 + 1] && grid[ligne * 3] == grid[ligne * 3 + 2])
            return true;
        return false;
    }

    private static boolean colonneGagnante(Joueur[] grid) {
        for (int colonne = 0; colonne < 3; colonne++) {
            if (colonneGagnante(colonne, grid)) return true;
        }
        return false;
    }

    private static boolean colonneGagnante(int colonne, Joueur[] grid) {
        if (grid[colonne] != null && grid[colonne] == grid[3 + colonne] && grid[colonne] == grid[6 + colonne])
            return true;
        return false;
    }

    private static boolean diagonaleGagnante(Joueur[] grid) {
        if (grid[0] != null && grid[0] == grid[4] && grid[0] == grid[8])
            return true;
        if (grid[2] != null && grid[2] == grid[4] && grid[2] == grid[6])
            return true;
        return false;
    }

}
