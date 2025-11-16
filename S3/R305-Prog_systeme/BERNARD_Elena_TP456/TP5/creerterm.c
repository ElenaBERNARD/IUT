// gcc -g -Og -Wall -Wextra -o creerterm creerterm.c
// ./creerterm
#include <unistd.h>
#include <stdio.h>

int main() {
    char* arg[] = { "xterm", NULL };
    // Ici execvp prend le format 
    // int execvp(const char *file, const char *argv[]);
    // Ou file est le nom de la commande a execute
    // argv[0] le nom du programme
    // argv[1] les arguments a ajouter a la commande (ici aucun)
    execvp("xterm", arg);

    // Ici, si la execvp s'execute correctement, le programme ne poursuis pas et est remplacé par xterm
    // En cas d'erreur, le programme poursuis et execute les lignes suivantes :
    perror("Erreur lors de l'exécution de xterm");
    return 1; 
}
