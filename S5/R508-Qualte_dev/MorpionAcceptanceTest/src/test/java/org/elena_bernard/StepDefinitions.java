package org.elena_bernard;

import io.cucumber.java.en.*;
import tictactoe.Grille;
import tictactoe.Human;
import tictactoe.IA;
import tictactoe.Joueur;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


public class StepDefinitions {
    Grille grille;
    IA ia;
    Human joueur;
    String motifIA = "X";
    Exception exception;

    @Given("un plateau vide")
    public void unPlateauVide() {
        grille = new Grille();
        joueur = new Human(null);
        ia = new IA(motifIA);
    }

    @Given("un plateau :")
    public void unPlateau(io.cucumber.datatable.DataTable dataTable) throws Exception {
        List<List<String>> rows = dataTable.asLists(String.class);
        grille = new Grille();
        ia = new IA(motifIA);
        joueur = new Human(null);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String cell = rows.get(i).get(j);
                if (cell != null) {
                    cell = cell.trim();
                }

                if ("X".equals(cell)) {
                    grille.joue(i * 3 + j, ia);
                } else if ("O".equals(cell)) {
                    grille.joue(i * 3 + j, joueur);
                }
            }
        }
    }

    @When("l'IA joue")
    public void lIAJoue() {
        ia.joue(grille);
    }

    @Then("l'IA a joue dans un coin")
    public void lIAAJoueDansUnCoin() {
        List<Integer> placements = grille.casesJoueur(ia);
        for (Integer placement : placements) {
            System.out.println(placement);
        }
        assertThat(placements.contains(0) || placements.contains(2) || placements.contains(6) || placements.contains(8)).isTrue();
    }

    @And("le joueur joue dans un coin")
    public void leJoueurJoueDansUnCoin() {
        try {
            grille.joue(0, joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("l'IA a joue en haut a droite")
    public void lIAAJoueEnHautADroite() {
        List<Integer> placements = grille.casesJoueur(ia);
        assertThat(placements.contains(2)).isTrue();
    }

    @Then("l'IA a joue en bas a droite")
    public void lIAAJoueEnBasADroite() {
        List<Integer> placements = grille.casesJoueur(ia);
        assertThat(placements.contains(8)).isTrue();
    }

    @Then("l'IA a joue en haut au milieu")
    public void lIAAJoueEnHautAuMilieu() {
        List<Integer> placements = grille.casesJoueur(ia);
        assertThat(placements.contains(1)).isTrue();
    }

    @When("le joueur joue en bas a droite")
    public void leJoueurJoueEnBasADroite() {
        try {
            grille.joue(8, joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("la partie est une egalite")
    public void laPartieEstUneEgalite() {
        assertThat(grille.isPat()).isTrue();
    }

    @Then("le coup doit etre rejete")
    public void leCoupDoitEtreRejete() {
        assertThat(exception).isInstanceOf(Exception.class);
    }

    @And("le joueur joue au centre")
    public void leJoueurJoueAuCentre() {
        try {
            grille.joue(4, joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("le centre appartient au joueur")
    public void leCentreAppartientAuJoueur() {
        List<Integer> placements = grille.casesJoueur(joueur);
        assertThat(placements.contains(4)).isTrue();
    }

    @And("le joueur joue en haut a gauche")
    public void leJoueurJoueEnHautAGauche() {
        try {
            grille.joue(0, joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("l'IA joue en haut a droite")
    public void lIAJoueEnHautADroite() {
        try {
            grille.joue(2, ia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("le joueur joue a gauche au milieu")
    public void leJoueurJoueAGaucheAuMilieu() {
        try {
            grille.joue(3, joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("l'IA joue a droite au milieu")
    public void lIAJoueADroiteAuMilieu() {
        try {
            grille.joue(5, ia);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("le joueur joue en bas a gauche")
    public void leJoueurJoueEnBasAGauche() {
        try {
            grille.joue(6, joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("le joueur gagne")
    public void leJoueurGagne() {
        assertThat(grille.isGagnante()).isTrue();
        assertThat(grille.gagnant()).isEqualTo(joueur);
    }

    @Then("l'IA a joue au centre")
    public void lIAAJoueAuCentre() {
        List<Integer> placements = grille.casesJoueur(ia);
        assertThat(placements.contains(4)).isTrue();
    }

    @When("le joueur joue deux fois au centre")
    public void leJoueurJoueDeuxFoisAuCentre() {
        Scanner sc = new Scanner("2,2\n2,2\n3");
        joueur = new Human(sc);
        joueur.joue(grille);
        try {
            joueur.joue(grille);
        } catch (Exception e) {
            exception = e;
        }
    }
}
