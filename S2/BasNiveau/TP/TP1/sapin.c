#include <stdio.h>
#include <math.h>

int main() {
    int h;
    printf("Entrez une hauteur h (un entier)\n");
    scanf("%i", &h);

    for(int i = h-1; i >= 0; i--){
        for(int j = 0; j < i; j++){
            printf(" ");
        }
        for(int j = 0; j < (h-i)*2-1; j++){
            printf("*");
        }
        for(int j = 0; j < i; j++){
            printf(" ");
        }
        printf("\n");
    }

    for(int i = 0; i < h; i++){
        for(int j = 0; j < h*2-1; j++){
            if(((i+j)== h-1 || (i+j) == h-1+2*i) || (i == h-1)){
                printf("*");
            }
            else{
                printf(" ");
            }
        }
        printf("\n");
    }

    for(int i=0; i < h*2-1; i++){
        
    } 
    return 0;
}