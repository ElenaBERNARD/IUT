# language: en
Feature: Règles de base du jeu

  Scenario: Placement valide alterne X puis O
    Given un plateau vide
    When le joueur joue au centre
    Then le centre appartient au joueur

  Scenario: Refuser un coup sur une case occupée
    Given un plateau vide
    And  le joueur joue au centre
    When le joueur joue au centre
    Then le coup est rejeté avec l'erreur case occupée
