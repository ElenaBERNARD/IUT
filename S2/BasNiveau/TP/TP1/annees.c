#include <stdio.h>
#include <math.h>

int main() {
    int annee;
    printf("Entrez une annee (un entier)\n");
    scanf("%i", &annee);

    if ((!(annee%100 == 0) && annee%4 == 0) || annee%400==0) {
        printf("Annee bissextiles !\n");
    }
    else {
        printf("Annee non bissextiles...\n");
    }
    return 0;
}