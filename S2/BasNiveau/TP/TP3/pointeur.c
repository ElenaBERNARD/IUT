// gcc -g -Og -Wall -Wextra -o pointeur pointeur.c
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

int main()
{
    // float a = 0.001;
    // float b = 0.003;
    // float c, *pa, *pb, *pp;
    // printf("a=%f b=%f\n", a, b);
    // pa = &a;
    // printf("a=%f b=%f pa=%p\n", a, b, pa);
    // *pa *= 2;
    // printf("a=%f b=%f pa=%p\n", a, b, pa);
    // pb = &b;
    // printf("a=%f b=%f pa=%p pb=%p\n", a, b, pa, pb);
    // c = 3 * (*pb - *pa);
    // printf("a=%f b=%f c=%f pa=%p pb=%p\n", a, b, c, pa, pb);
    // pp = pa;
    // printf("a=%f b=%f c=%f pa=%p pb=%p pp=%p\n", a, b, c, pa, pb, pp);

    // int n;
    // printf("Entrer le nombre de notes :");
    // scanf("%i", &n);


    // double *tab = malloc(n * sizeof(double));

    // for(int i = 0; i < n; i++){
    //     printf("Enter grade : (%i remaining) : ", (n-i));
    //     scanf("%lf", &tab[i]);
    // }

    // float min = tab[0];
    // float max = tab[0];
    // float sum = tab[0];
    // int med1, med2;
    // float median = 0;
    // if(n%2 != 0){
    //     med1 = n/2;
    //     med2 = n/2;
    // }
    // else{
    //     med1 = (n-1)/2;
    //     med2 = (n+1)/2;
    // }
    // for(int i = 1; i < n; i++){
    //     if(min > tab[i]){
    //         min = tab[i];
    //     }
    //     if(max < tab[i]){
    //         max = tab[i];
    //     }
    //     if(i == med1){
    //         median = tab[i];
    //     }
    //     if(i == med2){
    //         median += tab[i];
    //         median /= 2;
    //     }
    //     sum += tab[i];
    // }
    // float mean = sum / n;

    // printf("Min=%f, Max=%f\nMean=%f, Median=%f\n", min, max, mean, median);
    // free(tab);

    int n, m;
    printf("Entrer le nombre de lignes : ");
    scanf("%i", &n);
    printf("Entrer le nombre de colones : ");
    scanf("%i", &m);


    int **tab = (int**)malloc((n * sizeof(int*)));
    for(int i=0; i < n; i++){
        tab[i] = (int*)malloc(m * sizeof(int));
    }

    for(int i=0; i < n; i++){
        for(int j=0; j < m; j++){
            tab[i][j] = i*j;
        }
    }

    for(int i=0; i < n; i++){
        for(int j=0; j < m; j++){
            if(tab[i][j] < 10){
                printf("0");
            }
            printf("%i ", tab[i][j]);
        }
        printf("\n");
    }

    for(int i=0; i < n; i++){
        free(tab[i]);
    }
    free(tab);
    return 0;
}
