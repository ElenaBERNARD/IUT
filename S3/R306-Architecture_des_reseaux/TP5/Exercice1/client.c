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
        erreur("Error: Numero port invalide");
    }

    // Creation de la socket UDP
    int sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (sock < 0)
    {
        erreur("Error: Echec de la creation de la socket");
    }

    // Configuration de l'adresse du serveur
    struct sockaddr_in server_address;
    memset(&server_address, 0, sizeof(server_address));
    server_address.sin_family = AF_INET;
    server_address.sin_port = htons(port);
    if (inet_aton(hostname, &server_address.sin_addr) == 0)
    {
        close(sock);
        erreur("adresse invalide");
    }

    printf("Envoi des données à %s sur le port %d\n", hostname, port);

    // Lecture de l'entrée standard et envoi des données
    char buffer[BUFFER_SIZE];
    while (fgets(buffer, sizeof(buffer), stdin) != NULL)
    {
        ssize_t bytes_sent = sendto(sock, buffer, strlen(buffer), 0,
                                    (struct sockaddr *)&server_address, sizeof(server_address));
        if (bytes_sent < 0)
        {
            close(sock);
            erreur("Error: L'envoi des données a échoué");
        }

        // Réception de la réponse du serveur (facultatif)
        struct sockaddr_in response_address;
        socklen_t response_address_len = sizeof(response_address);
        ssize_t bytes_received = recvfrom(sock, buffer, sizeof(buffer) - 1, 0,
                                          (struct sockaddr *)&response_address, &response_address_len);
        if (bytes_received < 0)
        {
            close(sock);
            erreur("Error: La réception des données a échoué");
        }

        buffer[bytes_received] = '\0'; // Null-terminate the received string
        printf("Réponse du serveur : %s", buffer);
    }

    // Fermeture de la socket
    close(sock);
    printf("Connexion terminée.\n");
    return 0;
}
