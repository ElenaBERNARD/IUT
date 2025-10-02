#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <errno.h>

#define BUFFER_SIZE 1024
#define SERVER_BACKLOG 16

void erreur(char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        fprintf(stderr, "Usage : %s port\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int port = atoi(argv[1]);
    if (port <= 0)
    {
        erreur("Error: Numero port invalide\n");
    }

    // Création de la socket
    int server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket < 0)
    {
        erreur("Error: Echec de la creation de la socket");
    }

    // Configuration de l'adresse du serveur
    struct sockaddr_in server_address;
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    server_address.sin_addr.s_addr = htonl(INADDR_ANY);

    // Liaison de la socket à l'adresse et au port
    if (bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)) < 0)
    {
        close(server_socket);
        erreur("Error: Echec de l'association de la socket au port");
    }

    // Passage en mode écoute
    if (listen(server_socket, SERVER_BACKLOG) < 0)
    {
        close(server_socket);
        erreur("Error: Echec de la mise en ecoute de la socket");
    }

    printf("Serveur en ecoute sur le port %d\n", port);

    while (1)
    {
        // Accepter une connexion entrante
        struct sockaddr_in client_address;
        socklen_t client_address_len = sizeof(client_address);
        int client_socket = accept(server_socket, (struct sockaddr *)&client_address, &client_address_len);
        if (client_socket < 0)
        {
            perror("Error: Echec de connexion avec le client");
            continue; // Ne pas quitter le serveur, continuer à écouter
        }

        // Afficher des informations sur le client
        char client_ip[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, &client_address.sin_addr, client_ip, sizeof(client_ip));
        printf("Connection de %s:%d\n", client_ip, ntohs(client_address.sin_port));

        // Lire les données du client et les afficher sur la sortie standard
        char buffer[BUFFER_SIZE];
        ssize_t nb_bytes;
        while ((nb_bytes = read(client_socket, buffer, sizeof(buffer) - 1)) > 0)
        {
            buffer[nb_bytes] = '\0'; // Null-terminate pour l'affichage
            printf("%s", buffer);
        }

        if (nb_bytes < 0)
        {
            perror("Error: Echec de la lecture du message du client");
        }

        // Fermer la connexion avec le client
        close(client_socket);
        printf("Connection cloturee de %s:%d\n", client_ip, ntohs(client_address.sin_port));
    }

    // Fermer la socket du serveur (jamais atteint dans cette boucle infinie)
    close(server_socket);
    return 0;
}
