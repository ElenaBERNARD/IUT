#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include "../client_serveur.h"

#define MAX_ELEMENTS 16
#define DEBUG 0

void traiter_client(int client_socket)
{
    unsigned int taille;
    int tableau[MAX_ELEMENTS];
    int tableau_filtre[MAX_ELEMENTS];
    unsigned int taille_filtre = 0;

    // Lire la taille du tableau
    if (read(client_socket, &taille, sizeof(unsigned int)) <= 0)
    {
        perror("Erreur lors de la lecture de la taille");
        close(client_socket);
        return;
    }

    if (taille > MAX_ELEMENTS)
    {
        printf("Taille du tableau trop grande : %u\n", taille);
        close(client_socket);
        return;
    }

    // Lire le tableau
    if (read(client_socket, tableau, taille * sizeof(int)) <= 0)
    {
        perror("Erreur lors de la lecture du tableau");
        close(client_socket);
        return;
    }

    printf("Tableau reçu : ");
    for (unsigned int i = 0; i < taille; i++)
    {
        printf("%d ", tableau[i]);
    }
    printf("\n");

    // Filtrer les zéros
    for (unsigned int i = 0; i < taille; i++)
    {
        if (tableau[i] != 0)
        {
            tableau_filtre[taille_filtre++] = tableau[i];
        }
    }

    // Envoyer la nouvelle taille
    if (write(client_socket, &taille_filtre, sizeof(unsigned int)) <= 0)
    {
        perror("Erreur lors de l'envoi de la taille filtrée");
        close(client_socket);
        return;
    }

    // Envoyer le tableau filtré
    if (write(client_socket, tableau_filtre, taille_filtre * sizeof(int)) <= 0)
    {
        perror("Erreur lors de l'envoi du tableau filtré");
        close(client_socket);
        return;
    }

    printf("Tableau filtré envoyé : ");
    for (unsigned int i = 0; i < taille_filtre; i++)
    {
        printf("%d ", tableau_filtre[i]);
    }
    printf("\n");

    close(client_socket);
}

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        fprintf(stderr, "Usage: %s port\n", argv[0]);
        return 1;
    }

    int port = atoi(argv[1]);
    int lsock = creer_serveur_tcp(port, DEBUG);
    if (lsock < 0)
    {
        perror("Erreur lors de la création du serveur");
        return EXIT_FAILURE;
    }

    printf("Serveur parallèle (processus) avec tableau statique en écoute sur le port %d\n", port);

    while (1)
    {
        int client_socket = attendre_client_tcp(lsock, DEBUG);
        if (client_socket < 0)
        {
            perror("Erreur lors de l'attente d'un client");
            continue;
        }

        pid_t pid = fork();
        if (pid < 0)
        {
            perror("Erreur lors du fork");
            close(client_socket);
            continue;
        }

        if (pid == 0)
        {                 // Processus enfant
            close(lsock); // L'enfant n'écoute pas d'autres clients
            traiter_client(client_socket);
            exit(EXIT_SUCCESS);
        }
        else
        {                         // Processus parent
            close(client_socket); // Le parent continue d'écouter d'autres clients
            // Gestion des processus zombies
            while (waitpid(-1, NULL, WNOHANG) > 0);
        }
    }

    close(lsock);
    return EXIT_SUCCESS;
}
