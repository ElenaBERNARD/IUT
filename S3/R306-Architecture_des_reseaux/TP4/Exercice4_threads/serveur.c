#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <pthread.h>

#define BUFFER_SIZE 1024
#define SERVER_BACKLOG 16

#define COLOR_RESET "\e[0m"
#define GRN "\e[1;32m"

void erreur(const char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

// Structure pour passer les données au thread
struct client_data
{
    int client_socket;
    struct sockaddr_in client_address;
};

// Fonction exécutée par chaque thread
void *traiter_client(void *arg)
{
    // Lire les données du client et les afficher sur la sortie standard
    
    // Création des variables locales
    char buffer[BUFFER_SIZE];
    char client_ip[INET_ADDRSTRLEN];
    ssize_t nb_bytes;
    int client_port;
    int client_socket;
    struct client_data *data = (struct client_data *)arg;

    client_socket = data->client_socket;
    struct sockaddr_in client_address = data->client_address;
    free(data); // Liberer la memoire allouee pour `client_data`

    

    // CODE NON ORIGINAL
    // Convertit l'adresse IP binaire du client (contenue dans client_address.sin_addr) en une chaîne de caractères lisible (format IPv4).
    // - AF_INET : spécifie que l'adresse est au format IPv4.
    // - &client_address.sin_addr : adresse binaire à convertir.
    // - client_ip : tableau où sera stockée l'adresse convertie sous forme lisible.
    // - sizeof(client_ip) : taille maximale du tableau pour éviter un débordement.
    inet_ntop(AF_INET, &client_address.sin_addr, client_ip, sizeof(client_ip));
    client_port = ntohs(client_address.sin_port);
    // FIN CODE NON ORIGINAL

    while ((nb_bytes = read(client_socket, buffer, sizeof(buffer) - 1)) > 0)
    {
        buffer[nb_bytes] = '\0'; // Ajout \0 pour l'affichage
        printf("%s%s:%d %s: %s", GRN, client_ip, client_port, COLOR_RESET, buffer);
    }

    if (nb_bytes < 0)
    {
        perror("Error: Echec de la lecture du message du client");
    }

    // Fermer la connexion avec le client
    close(client_socket);
    return NULL;
}

int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        fprintf(stderr, "Usage: %s port\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int port = atoi(argv[1]);
    if (port <= 0)
    {
        erreur("Error: Numéro de port invalide\n");
    }

    // Création de la socket serveur
    int server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket < 0)
    {
        erreur("Error: Échec de la création de la socket");
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
        erreur("Error: Échec de l'association de la socket au port");
    }

    // Passage en mode écoute
    if (listen(server_socket, SERVER_BACKLOG) < 0)
    {
        close(server_socket);
        erreur("Error: Échec de la mise en écoute de la socket");
    }

    printf("Serveur en écoute sur le port %d\n", port);

    while (1)
    {
        // Accepter une connexion entrante
        struct sockaddr_in client_address;
        socklen_t client_address_len = sizeof(client_address);
        int client_socket = accept(server_socket, (struct sockaddr *)&client_address, &client_address_len);
        if (client_socket < 0)
        {
            perror("Error: Échec de connexion avec le client");
            continue; // Ne pas quitter le serveur, continuer à écouter
        }

        // Allouer une structure pour les données du client
        struct client_data *data = malloc(sizeof(struct client_data));
        if (!data)
        {
            perror("Error: Allocation mémoire échouée");
            close(client_socket);
            continue;
        }

        data->client_socket = client_socket;
        data->client_address = client_address;

        // Créer un thread pour gérer la connexion du client
        pthread_t thread_id;
        if (pthread_create(&thread_id, NULL, traiter_client, data) != 0)
        {
            perror("Error: Échec de la création du thread");
            free(data); // Libérer la mémoire si le thread échoue
            close(client_socket);
            continue;
        }

        // Détacher le thread pour éviter de bloquer sur `pthread_join`
        pthread_detach(thread_id);
    }

    // Fermer la socket du serveur (jamais atteint dans cette boucle infinie)
    close(server_socket);
    return 0;
}