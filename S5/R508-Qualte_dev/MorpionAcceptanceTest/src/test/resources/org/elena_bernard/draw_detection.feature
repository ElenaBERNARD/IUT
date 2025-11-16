Feature: Detection de match nul

    Scenario: Grille pleine sans alignement = match nul
        Given un plateau :
          | X | O | X |
          | X | O | O |
          | O | X |   |
        When le joueur joue en bas a droite
        Then la partie est une egalite
