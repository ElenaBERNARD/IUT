#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include "../client_serveur.h"

#define DEBUG 0

void envoyer_tableau(int socket, int *tableau, unsigned int taille) {
    // Envoyer la taille
    if (write(socket, &taille, sizeof(unsigned int)) <= 0) {
        perror("Erreur lors de l'envoi de la taille");
        return;
    }

    // Envoyer les éléments
    if (write(socket, tableau, taille * sizeof(int)) <= 0) {
        perror("Erreur lors de l'envoi du tableau");
        return;
    }
}

void recevoir_tableau(int socket) {
    unsigned int taille;
    if (read(socket, &taille, sizeof(unsigned int)) <= 0) {
        perror("Erreur lors de la réception de la taille");
        return;
    }

    int *tableau = malloc(taille * sizeof(int));
    if (!tableau) {
        perror("Erreur d'allocation mémoire");
        return;
    }

    if (read(socket, tableau, taille * sizeof(int)) <= 0) {
        perror("Erreur lors de la réception du tableau");
        free(tableau);
        return;
    }

    printf("Tableau reçu : ");
    for (unsigned int i = 0; i < taille; i++) {
        printf("%d ", tableau[i]);
    }
    printf("\n");

    free(tableau);
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s serveur port\n", argv[0]);
        return 1;
    }
    char *serveur = argv[1];
    int port = atoi(argv[2]);

    // Connexion au serveur
    int sock = creer_client_tcp(serveur, port, DEBUG);
    if (sock < 0) {
        perror("Erreur lors de la connexion au serveur");
        return EXIT_FAILURE;
    }

    // Premier tableau : 10 éléments avec des zéros
    int tableau1[10] = {1, 0, 2, 0, 3, 0, 4, 0, 5, 0};
    printf("Tableau envoyé : ");
    for (int i = 0; i < 10; i++) {
        printf("%d ", tableau1[i]);
    }
    printf("\n");

    envoyer_tableau(sock, tableau1, 10);
    recevoir_tableau(sock);
    close(sock);

    // Deuxième tableau : 256 éléments
    sock = creer_client_tcp(serveur, port, DEBUG);
    int tableau2[1000000];
    for (int i = 0; i < 1000000; i++) {
        tableau2[i] = i % 2;
    }

    printf("Tableau envoyé : ");
    // for (int i = 0; i < 1000000; i++) {
    //     printf("%d ", tableau2[i]);
    // }
    printf("\n");

    envoyer_tableau(sock, tableau2, 1000000);
    recevoir_tableau(sock);

    close(sock);
    return EXIT_SUCCESS;
}
