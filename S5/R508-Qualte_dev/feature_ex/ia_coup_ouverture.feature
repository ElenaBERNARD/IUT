# language: en
Feature: Coup d'ouverture par l'IA

  Scenario: Coup d'ouverture - premier coup
    Given un plateau vide
    When l'IA joue
    Then l'IA à joué dans un coin

  Scenario: Coup d'ouverture — deuxième coup- centre occupé
    Given un plateau vide
    And le joueur joue au centre
    When l'IA joue
    Then l'IA à joué dans un coin

  Scenario: Coup d'ouverture — deuxième coup - centre libre
    Given un plateau vide
    And le joueur joue dans le coin supérieur gauche
    When l'IA joue
    Then l'IA à joué au centre

