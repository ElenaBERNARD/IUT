#include <stdio.h>
#include <math.h>

int main()
{
    int tab[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

    int taille = sizeof(tab) / sizeof(tab[0]);
    printf("Taille : %i", taille);
    return 0;
}
