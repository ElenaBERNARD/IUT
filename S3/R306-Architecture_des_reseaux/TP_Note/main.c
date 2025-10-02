#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char const *argv[])
{
    if(argc != 2)
    {
        fprintf(stderr, "Usage : %s port utilise\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int port = atoi(argv[1]);

    if(port <= 0)
    {
        fprintf(stderr, "Error: Numero port invalide\n");
        exit(EXIT_FAILURE);
    }

    
    return 0;
}
