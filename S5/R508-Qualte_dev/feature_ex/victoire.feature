# language: en
Feature: Détection de victoire

  Scenario: (Concret) Victoire sur une ligne
    Given un plateau vide
    And le joueur joue dans le coin supérieur gauche
    And l'ia joue au bord gauche
    And le joueur joue dans le bord supérieur
    And l'ia joue au centre
    When le joueur joue dans le coin supérieur droit
    Then le joueur gagne

