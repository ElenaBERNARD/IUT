Feature: Detection de victoire

    Scenario: Victoire en ligne horizontale
        Given un plateau vide
        And le joueur joue en haut a gauche
        And l'IA joue en haut a droite
        And le joueur joue a gauche au milieu
        And l'IA joue a droite au milieu
        And le joueur joue en bas a gauche
        Then le joueur gagne
