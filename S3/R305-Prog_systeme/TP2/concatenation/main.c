#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/stat.h> // Pour umask et permissions
#include <fcntl.h>
#include <unistd.h>


#define BUFFER_SIZE 4096

void copier_fichier(FILE *src, FILE *cible) {
    char buffer[BUFFER_SIZE];
    size_t bytes_read;

    // Lire depuis le fichier source et écrire dans le fichier destination
    while ((bytes_read = fread(buffer, 1, sizeof(buffer), src)) > 0) {
        if (fwrite(buffer, 1, bytes_read, cible) != bytes_read) {
            perror("Erreur lors de l'écriture dans le fichier destination");
            exit(EXIT_FAILURE);
        }
    }

    if (ferror(src)) {
        perror("Erreur lors de la lecture du fichier source");
        exit(EXIT_FAILURE);
    }
}

FILE *ouvrir_source(const char *fichier) {
    if (strcmp(fichier, "-") == 0) {
        return stdin;  // Utilise l'entrée standard si "-" est fourni
    }

    FILE *f = fopen(fichier, "rb");
    if (!f) {
        perror("Erreur lors de l'ouverture du fichier source");
        exit(EXIT_FAILURE);
    }
    return f;
}

int main(int argc, char *argv[]) {
    if (argc < 2) {
        fprintf(stderr, "Usage: %s [src1 src2 src3 ...] dest\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    // La destination est le dernier argument
    const char *fichier_cible = argv[argc - 1];

    // Masquer les permissions par défaut pour ne permettre que rw------- (600)
    mode_t old_umask = umask(S_IWGRP | S_IRGRP | S_IROTH | S_IWOTH);

    // Ouvrir le fichier destination avec les permissions rw-------
    FILE *cible = fopen(fichier_cible, "wb");
    if (!cible) {
        perror("Erreur lors de la création du fichier destination");
        exit(EXIT_FAILURE);
    }

    // Restaurer l'umask original
    umask(old_umask);

    // Si le nombre de paramètres est égal à 1, copier depuis l'entrée standard
    if (argc == 2) {
        copier_fichier(stdin, cible);
    } else {
        // Copier chaque fichier source dans le fichier destination
        for (int i = 1; i < argc - 1; i++) {
            FILE *src = ouvrir_source(argv[i]);
            copier_fichier(src, cible);
            if (src != stdin) {
                fclose(src);
            }
        }
    }

    fclose(cible);
    return 0;
}
