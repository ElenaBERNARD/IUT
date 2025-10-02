// client.c
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
    if (argc != 3) {
        fprintf(stderr, "Usage: %s serveur port\n", argv[0]);
        return 1;
    }
    char *serveur = argv[1];
    int port = atoi(argv[2]);
    int ret, taille, somme;

    // Connexion au serveur
    int sock = creer_client_tcp(serveur, port, DEBUG);
    VERIFIER(sock != -1);

    // Envoi d'un entier
    unsigned int nombre;
    printf("Entrez un entier: ");
    scanf("%u", &nombre);
    ret = write(sock, &nombre, sizeof(unsigned int));
    VERIFIER(ret == sizeof(unsigned int));

    // Envoi d'un tableau d'entiers
    printf("Taille du tableau: ");
    scanf("%d", &taille);
    unsigned int *tableau = malloc(taille * sizeof(unsigned int));
    printf("Entrez les éléments du tableau:\n");
    for (int i = 0; i < taille; i++) {
        scanf("%u", &tableau[i]);
    }

    // Envoi de la taille du tableau
    ret = write(sock, &taille, sizeof(int));
    VERIFIER(ret == sizeof(int));

    // Envoi des éléments du tableau
    ret = write(sock, tableau, taille * sizeof(unsigned int));
    VERIFIER(ret == taille * sizeof(unsigned int));

    // Réception de la somme calculée
    ret = read(sock, &somme, sizeof(int));
    VERIFIER(ret == sizeof(int));

    printf("Somme reçue du serveur: %d\n", somme);

    close(sock);
    free(tableau);
    return 0;
}
