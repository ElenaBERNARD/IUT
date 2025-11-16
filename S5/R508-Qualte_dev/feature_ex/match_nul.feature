# language: en
Feature: Détection de match nul

  Scenario: Plateau plein sans vainqueur déclenche match nul
    Given un plateau avec:
      | x | x | O |
      | O | O | x |
      | X | O |   |
    When le joueur joue dans le coin inférieur droit
    Then le plateau possède une egalité
