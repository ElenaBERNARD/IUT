#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <unistd.h>

int main(){
    // Ne laisser les droits de lecture qu'a l'utilisateur (S_IRUSR), les autres sont masques
    int previous_umask = umask(S_IWUSR | S_IXUSR | S_IRGRP | S_IWGRP | S_IXGRP | S_IROTH | S_IWOTH | S_IXOTH);

    FILE *fichierSortieBin = fopen("res.bin", "wb");
    FILE *fichierSortieDec = fopen("res.ascii", "w");
    FILE *fichierSortieHex = fopen("res.hex", "w");

    umask(previous_umask);

    int tab[60];
    for(int i = 0; i < 60; i++){
        tab[i] = i+1;
        printf("%02d\n", tab[i]);
    }

    // Écriture en binaire dans res.bin
    if (fwrite(tab, sizeof(int), 60, fichierSortieBin) != 60) {
        printf("Erreur lors de l'écriture dans res.bin");
    }
    fclose(fichierSortieBin);

    // Écriture en texte décimal dans res.ascii
    for (int i = 0; i < 60; i++) {
        fprintf(fichierSortieDec, "%03d\n", tab[i]); // Chaque nombre sur 3 caractères
    }
    fclose(fichierSortieDec);

    // Écriture en texte hexadécimal dans res.hex
    for (int i = 0; i < 60; i++) {
        fprintf(fichierSortieHex, "%08x\n", tab[i]); // Chaque nombre sur 8 chiffres en hexadécimal
    }
    fclose(fichierSortieHex);
    return 0;
}