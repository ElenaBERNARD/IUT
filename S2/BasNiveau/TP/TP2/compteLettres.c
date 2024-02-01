#include <stdio.h>
#include <math.h>

int main()
{
    int maxLength = 50;

    char letters[52];
    int nbletters[52];
    
    for (int i = 0; i < 26; i++) {
        letters[i] = 'a' + i;
        nbletters[i] = 0;
    }
    for (int i = 26; i < 52; i++) {
        letters[i] = 'A' + i - 26;
        nbletters[i] = 0;
    }

    char str[maxLength];
    fgets(str, maxLength, stdin);


    for(int i=0; i < 52; i++){
        for(int j=0; j < maxLength; j++){
            if(str[j]==letters[i]){
                nbletters[i]++;
            }
        }
    }

    for (int i = 0; i < 52; i++) {
        printf("\n%c : ", letters[i]);
        for(int j = 0; j < nbletters[i]; j++){
            printf("-");
        }
    }
    printf("\n");
    return 0;
}