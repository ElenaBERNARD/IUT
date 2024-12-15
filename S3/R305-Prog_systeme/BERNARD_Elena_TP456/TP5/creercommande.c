// gcc -g -Og -Wall -Wextra -o creercommande creercommande.c
// ./creercommande ls

#include <unistd.h>
#include <stdio.h>

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Veuillez saisir un argument correspondant a une commande : \n./%s nom_de_la_commande\n", argv[0]);
        return 1;
    }

    // Ici, on ajoute des parametre a xterm pour lancer la commande contenue dans argv[1]
    char *xterm_args[] = { "xterm", "-hold", "-e", argv[1], NULL };

    execvp("xterm", xterm_args);

    // Comme precedement, si la execvp s'execute correctement, le programme ne poursuis pas et est remplacé par xterm
    // En cas d'erreur, le programme poursuis et execute les lignes suivantes :
    perror("Erreur lors de l'exécution de xterm");
    return 1;
}