#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <errno.h>

#define BUFFER_SIZE 1024

void erreur(const char *message)
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
        erreur("Error: Numero port invalide");
    }

    // Création de la socket UDP
    int server_socket = socket(AF_INET, SOCK_DGRAM, 0);
    if (server_socket < 0)
    {
        erreur("Error: Echec de la creation de la socket");
    }

    // Configuration de l'adresse du serveur
    struct sockaddr_in server_address;
    memset(&server_address, 0, sizeof(server_address));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    server_address.sin_addr.s_addr = htonl(INADDR_ANY);

    // Liaison de la socket à l'adresse et au port
    if (bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)) < 0)
    {
        close(server_socket);
        erreur("Error: Echec de l'association de la socket au port");
    }

    printf("Serveur en écoute sur le port %d\n", port);

    // Réception des données des clients
    char buffer[BUFFER_SIZE];
    struct sockaddr_in client_address;
    socklen_t client_address_len = sizeof(client_address);

    while (1)
    {
        ssize_t bytes_received = recvfrom(server_socket, buffer, sizeof(buffer) - 1, 0,
                                          (struct sockaddr *)&client_address, &client_address_len);
        if (bytes_received < 0)
        {
            perror("Error: Echec de la reception des données");
            continue; // Continuer à écouter
        }

        printf("caa\n");

        // Null-terminate the received message for printing
        buffer[bytes_received] = '\0';

        // Afficher les informations sur le client et le message
        char client_ip[INET_ADDRSTRLEN];
        inet_ntop(AF_INET, &client_address.sin_addr, client_ip, sizeof(client_ip));
        printf("Message de %s:%d - %s",
               client_ip, ntohs(client_address.sin_port), buffer);

        // Répondre au client
        const char *response = "Message reçu\n";
        ssize_t bytes_sent = sendto(server_socket, response, strlen(response), 0,
                                    (struct sockaddr *)&client_address, client_address_len);
        if (bytes_sent < 0)
        {
            perror("Error: Echec de l'envoi de la réponse");
        }
    }

    // Fermer la socket du serveur (jamais atteint dans cette boucle infinie)
    close(server_socket);
    return 0;
}
