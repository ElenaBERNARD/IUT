# language: en
Feature: Stratégie de placement de pion par l'IA

  Scenario: placement ia - coup gagnant ia
    Given un plateau avec:
      | X |   |   |
      |   | O |   |
      | O |   | X |
    When l'IA joue
    Then l'IA à jouer dans le coin supérieur droit


  Scenario: placement ia - coup gagnant joueur
    Given un plateau avec:
      | X |   |  |
      | O | x |  |
      |   | O |  |
    When l'IA joue
    Then l'IA à jouer dans le coin inférieur droit


  Scenario: placement ia - coup gagnant joueur - coup gagnant ia
    Given un plateau avec:
      | O | O |  |
      | x | x |  |
      |   |   |  |
    When l'IA joue
    Then l'IA à jouer dans le coin supérieur droit
