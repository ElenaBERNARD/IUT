// serveur_sequentiel.c
#include "client_serveur.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define DEBUG 0

#define VERIFIER(expr)                                                     \
    if (!(expr)) {                                                         \
        fprintf(stderr, "%s:%d: erreur: %s\n", __FILE__, __LINE__, #expr); \
        exit(2);                                                           \
    }

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s port\n", argv[0]);
        return 1;
    }
    int port = atoi(argv[1]);
    int ret;

    // Création du serveur
    int lsock = creer_serveur_tcp(port, DEBUG);
    VERIFIER(lsock != -1);

    while (1) {
        // Attente d'un client
        int sock = attendre_client_tcp(lsock, DEBUG);
        VERIFIER(sock != -1);

        // Lecture d'un entier
        unsigned int nombre;
        ret = read(sock, &nombre, sizeof(unsigned int));
        VERIFIER(ret == sizeof(unsigned int));
        printf("Nombre reçu: %u\n", nombre);

        // Lecture de la taille du tableau
        int taille;
        ret = read(sock, &taille, sizeof(int));
        VERIFIER(ret == sizeof(int));

        // Lecture des éléments du tableau et calcul de la somme
        unsigned int *tableau = malloc(taille * sizeof(unsigned int));
        ret = read(sock, tableau, taille * sizeof(unsigned int));
        VERIFIER(ret == taille * sizeof(unsigned int));

        int somme = 0;
        for (int i = 0; i < taille; i++) {
            somme += tableau[i];
        }

        // Envoi de la somme au client
        ret = write(sock, &somme, sizeof(int));
        VERIFIER(ret == sizeof(int));

        close(sock);
        free(tableau);
    }

    close(lsock);
    return 0;
}
