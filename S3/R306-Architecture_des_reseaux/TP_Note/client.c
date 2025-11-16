#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <errno.h>

// TP Note du 20/12/2024
// BERNARD Elena
// Groupe S3 A1

#define BUFFER_SIZE 1024

// Fonction d'affichage des erreurs
void erreur(const char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

int main(int argc, char *argv[])
{
    if (argc < 4)
    {
        fprintf(stderr, "Usage : %s adresse_serveur port_serveur message\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    // Recuperation des arguments
    const char *adresse_serveur = argv[1];
    int port_serveur = atoi(argv[2]);

    // Verification du port
    if (port_serveur <= 0)
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
    server_address.sin_port = htons(port_serveur);
    if (inet_aton(adresse_serveur, &server_address.sin_addr) == 0)
    {
        close(sock);
        erreur("adresse_serveur invalide");
    }

    // Recuperation du message
    char buffer[BUFFER_SIZE];
    int size = 0;
    strcat(buffer, argv[3]);
    for (int i = 4; i < argc; i++)
    {
        strcat(buffer, " ");
        strcat(buffer, argv[i]);
        size += strlen(argv[i]) + 1;
    }

    // Envoi des données
    ssize_t bytes_sent = sendto(sock, buffer, strlen(buffer), 0,
                                (struct sockaddr *)&server_address, sizeof(server_address));
    if (bytes_sent < 0)
    {
        close(sock);
        erreur("Error: L'envoi des données a échoué");
    }

    // Reception de la réponse du serveur
    socklen_t server_address_len = sizeof(server_address);
    ssize_t bytes_received = recvfrom(sock, buffer, sizeof(buffer) - 1, 0,
                                      (struct sockaddr *)&server_address, &server_address_len);
    if (bytes_received < 0)
    {
        close(sock);
        erreur("Error: La réception des données a échoué");
    }

    buffer[bytes_received] = '\0';
    printf("%s\n", buffer);

    // Fermeture de la socket
    close(sock);
    return 0;
}
