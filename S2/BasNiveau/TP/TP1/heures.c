#include <stdio.h>
#include <math.h>

int main(){
    int d1, d2;
    printf("Entrez une duree en seconde (un entier)\n");
    scanf("%i", &d1);
    printf("Entrez une autre duree en seconde (un entier)\n");
    scanf("%i", &d2);

    int h1, h2, m1, m2, s1, s2;
    h1 = floor(d1 / 3600);
    m1 = floor((d1 - h1*3600) / 60);
    s1 = d1 - h1*3600 - m1*60;

    h2 = floor(d2 / 3600);
    m2 = floor((d2 - h2*3600) / 60);
    s2 = d2 - h2*3600 - m2*60;

    printf("%i:%i:%i\n", h1, m1, s1);
    printf("%i:%i:%i\n", h2, m2, s2);

    h1 += h2;
    m1 += m2;
    s1 += s2;

    if(s1 > 59){
        s1 -= 60;
        m1++;
    }
    if(m1 > 59){
        m1 -= 60;
        h1++;
    }
    printf("%i:%i:%i\n", h1, m1, s1);
    return 0;
}