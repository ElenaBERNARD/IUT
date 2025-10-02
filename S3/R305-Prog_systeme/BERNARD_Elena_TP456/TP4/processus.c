// gcc -g -Og -Wall -Wextra -o processus processus.c
// ./processus
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

void affiche_car(pid_t pid)
{
    switch (pid)
    {
    case -1:
        perror("Erreur lors de la creation du processus fils");
        break;
    case 0:
        printf("Fils : Processus numéro : %d. Numero du processus pere : %d.\n", getpid(), getppid());
        break;
    default:
        printf("Pere : Processus numéro : %d. Numero du processus pere : %d.\n", getpid(), getppid());
        break;
    }
}

void attendre_fils() {
    int status;
    pid_t pid_fini = wait(&status);
    if (pid_fini > 0) {
        if (WIFEXITED(status)) {
            printf("Processus %d : mon fils %d s'est termine ", getpid(), pid_fini);
            if (WEXITSTATUS(status) == 0) {
                printf("normalement.\n");
            } else {
                printf("anormalement avec le code de retour %d.\n", WEXITSTATUS(status));
            }
        }
    }
}

int main() {
    pid_t pid_fils1, pid_fils2, pid_petit_fils;

    // Création du premier processus fils
    pid_fils1 = fork();
    if (pid_fils1 == -1) {
        perror("Erreur lors de la création du premier fils");
        return 1;
    }

    if (pid_fils1 == 0) {
        affiche_car(pid_fils1);
        pid_petit_fils = fork();
        if (pid_petit_fils == -1) {
            perror("Erreur lors de la creation du premier petit-fils");
            return 1;
        }

        if (pid_petit_fils == 0) {
            affiche_car(pid_petit_fils);
            return 1;
        } else {
            attendre_fils();
            return 0;
        }
    }

    // Création du deuxième processus fils
    pid_fils2 = fork();
    if (pid_fils2 == -1) {
        perror("Erreur lors de la creation du deuxieme fils");
        return 1;
    }

    if (pid_fils2 == 0) {
        affiche_car(pid_fils2);

        pid_petit_fils = fork();
        if (pid_petit_fils == -1) {
            perror("Erreur lors de la création du deuxieme petit-fils");
            return 1;
        }

        if (pid_petit_fils == 0) {
            affiche_car(pid_petit_fils);
            return 1;
        } else {
            attendre_fils();
            return 0;
        }
    }

    attendre_fils();
    attendre_fils();

    return 0;
}
