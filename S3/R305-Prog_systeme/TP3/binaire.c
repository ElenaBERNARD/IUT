#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>

int main() {
    int fd;
    int tab[100];

    for (int i = 0; i < 100; i++) {
        tab[i] = i;
    }

    fd = open("tab.bin", O_WRONLY | O_CREAT | O_TRUNC, 0644);
    if (fd == -1) {
        perror("Erreur d'ouverture du fichier");
        return 1;
    }

    if (write(fd, tab, sizeof(tab)) == -1) {
        perror("Erreur d'écriture dans le fichier");
        close(fd);
        return 1;
    }

    close(fd);

    return EXIT_SUCCESS;
}
