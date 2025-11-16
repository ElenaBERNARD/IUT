#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#define BUFFER_SIZE 1024

void erreur(char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        fprintf(stderr, "Usage : %s adresse port\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    // Recuperation des arguments
    const char *hostname = argv[1];
    int port = atoi(argv[2]);

    // Verification de la validite du port
    if (port <= 0)
    {
        erreur("Error: Numero port invalide\n");
    }

    // Creation de la socket
    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock < 0)
    {
        erreur("Error: Echec de la creation de la socket");
    }

    // Configuration de l'adresse du serveur
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    if (inet_aton(hostname, &server_address.sin_addr) == 0)
    {
        erreur("adresse invalide:\n");
    }

    // Connexion au serveur
    if (connect(sock, (struct sockaddr *)&server_address, sizeof(server_address)) < 0)
    {
        close(sock);
        erreur("Error: La connection au serveur a echoue");
    }
    printf("Connection reussi a %s sur le port %d\n", hostname, port);

    // Lecture de l'entrée standard et envoi des données
    char buffer[BUFFER_SIZE];
    while (fgets(buffer, sizeof(buffer), stdin) != NULL)
    {
        ssize_t bytes_written = write(sock, buffer, strlen(buffer));
        if (bytes_written < 0)
        {
            close(sock);
            erreur("Error: L'ecriture sur la socket a echoue");
        }
    }

    // Fermeture de la connexion
    close(sock);
    printf("Connection cloturee.\n");
    return 0;
}
