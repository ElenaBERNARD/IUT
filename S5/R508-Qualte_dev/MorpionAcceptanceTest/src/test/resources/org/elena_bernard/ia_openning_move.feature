Feature: Coup d'ouverture de l'IA

    Scenario: L'IA commence et choisit un coin
        Given un plateau vide
        When l'IA joue
        Then l'IA a joue dans un coin

    Scenario: Coin occupé, l'IA joue au centre
        Given un plateau vide
        And le joueur joue dans un coin
        When l'IA joue
        Then l'IA a joue au centre