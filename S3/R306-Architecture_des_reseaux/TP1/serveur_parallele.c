// serveur_parallele.c
#include "client_serveur.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

#define DEBUG 0

#define VERIFIER(expr)                                                     \
    if (!(expr)) {                                                         \
        fprintf(stderr, "%s:%d: erreur: %s\n", __FILE__, __LINE__, #expr); \
        exit(2);                                                           \
    }

void gestion_signal(int sig) {
    if (sig == SIGUSR1) {
        fprintf(stderr, "signal reçu, terminaison du serveur.\n");
        exit(0);
    } else if (sig == SIGCHLD) {
        while (waitpid(-1, NULL, WNOHANG) > 0);
    }
}

void traiter_client(int sock) {
    int ret;

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
    exit(0);  // Fin du processus fils
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "Usage: %s port\n", argv[0]);
        return 1;
    }
    int port = atoi(argv[1]);

    // Gestion des signaux
    signal(SIGUSR1, gestion_signal);
    signal(SIGCHLD, gestion_signal);

    // Création du serveur
    int lsock = creer_serveur_tcp(port, DEBUG);
    VERIFIER(lsock != -1);

    while (1) {
        // Attente d'un client
        int sock = attendre_client_tcp(lsock, DEBUG);
        VERIFIER(sock != -1);

        // Création d'un processus fils pour traiter le client
        pid_t pid = fork();
        if (pid == 0) {
            // Processus fils
            close(lsock);
            traiter_client(sock);
        } else if (pid > 0) {
            // Processus parent
            close(sock);
        } else {
            perror("Erreur de fork");
            exit(2);
        }
    }

    close(lsock);
    return 0;
}
