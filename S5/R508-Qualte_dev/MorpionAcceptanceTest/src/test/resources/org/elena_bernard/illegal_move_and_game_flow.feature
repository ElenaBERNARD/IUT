Feature: Gestion des coups illegaux et du jeu

    Scenario: Empecher de jouer sur une case occupée
        Given un plateau vide
        When le joueur joue deux fois au centre
        Then le coup doit etre rejete

    Scenario: Alterner les tours entre les joueurs
        Given un plateau vide
        And le joueur joue au centre
        Then le centre appartient au joueur
