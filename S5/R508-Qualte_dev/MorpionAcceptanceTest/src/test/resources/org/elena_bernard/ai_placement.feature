Feature: Stratégie de placement de pion par l'IA

    Scenario: L'IA joue pour gagner si elle a une ligne à compléter
        Given un plateau :
          | X | X |   |
          |   |   | O |
          | O |   |   |
        When l'IA joue
        Then l'IA a joue en haut a droite

    Scenario: L'IA bloque l'adversaire qui va gagner au prochain tour
        Given un plateau :
          | X |   |   |
          |   |   | X |
          | O | O |   |
      When l'IA joue
        Then l'IA a joue en bas a droite

    Scenario: Priorité: gagner > bloquer
        Given un plateau :
          |   |   |  |
          | O | X |  |
          | O | X |  |
      When l'IA joue
        Then l'IA a joue en haut au milieu