#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <pthread.h>
#include <errno.h>

#define BUFFER_SIZE 1024
#define SERVER_BACKLOG 16
#define MAX_CLIENTS 100

#define COLOR_RESET "\e[0m"
#define GRN "\e[1;32m"

typedef struct
{
    int socket;
    struct sockaddr_in address;
} client_info;

client_info clients[MAX_CLIENTS];
int client_count = 0;
pthread_mutex_t clients_mutex = PTHREAD_MUTEX_INITIALIZER;

void erreur(const char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

// Envoyer un message a tout les clients sauf l'emetteur
void broadcast_message(const char* client_info, const char *message, int sender_socket)
{
    pthread_mutex_lock(&clients_mutex);
    for (int i = 0; i < client_count; i++)
    {
        if (clients[i].socket != sender_socket)
        {
            if (send(clients[i].socket, client_info, strlen(client_info), 0) < 0)
            {
                perror("Error: Failed to client info");
            }
            if (send(clients[i].socket, message, strlen(message), 0) < 0)
            {
                perror("Error: Failed to send message");
            }
        }
    }
    pthread_mutex_unlock(&clients_mutex);
}

// Fonction threader pour traiter les messages des clients
void *client_handler(void *arg)
{
    client_info *client = (client_info *)arg;
    int client_socket = client->socket;
    char client_ip[INET_ADDRSTRLEN];
    inet_ntop(AF_INET, &client->address.sin_addr, client_ip, sizeof(client_ip));
    int client_port = ntohs(client->address.sin_port);

    printf("Client connected: %s:%d\n", client_ip, client_port);

    // Concatenation de l'adresse IP et du port du client
    char client_info [INET_ADDRSTRLEN + 6];
    sprintf(client_info, "%s:%d", client_ip, client_port);


    char buffer[BUFFER_SIZE];
    ssize_t nb_bytes;

    while ((nb_bytes = read(client_socket, buffer, sizeof(buffer) - 1)) > 0)
    {
        buffer[nb_bytes] = '\0'; // Null-terminate pour l'affichage
        printf("%s%s:%d %s: %s", GRN, client_ip, client_port, COLOR_RESET, buffer);


        // Envoyer le message a tout les clients
        broadcast_message(client_info, buffer, client_socket);
    }

    if (nb_bytes == 0)
    {
        printf("Client disconnected: %s:%d\n", client_ip, client_port);
    }
    else if (nb_bytes < 0)
    {
        perror("Error: Failed to read from client");
    }

    // Retirer le client de la liste
    pthread_mutex_lock(&clients_mutex);
    for (int i = 0; i < client_count; i++)
    {
        if (clients[i].socket == client_socket)
        {
            clients[i] = clients[client_count - 1]; // Remplacer le client par le dernier client afin de garder la liste sans trou
            client_count--;
            break;
        }
    }
    pthread_mutex_unlock(&clients_mutex);

    close(client_socket);
    free(client);
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
        erreur("Error: Invalid port number");
    }

    int server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket < 0)
    {
        erreur("Error: Socket creation failed");
    }

    struct sockaddr_in server_address = {
        .sin_family = AF_INET,
        .sin_port = htons(port),
        .sin_addr.s_addr = htonl(INADDR_ANY)};

    if (bind(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)) < 0)
    {
        close(server_socket);
        erreur("Error: Bind failed");
    }

    if (listen(server_socket, SERVER_BACKLOG) < 0)
    {
        close(server_socket);
        erreur("Error: Listen failed");
    }

    printf("Server listening on port %d\n", port);

    while (1)
    {
        struct sockaddr_in client_address;
        socklen_t client_address_len = sizeof(client_address);
        int client_socket = accept(server_socket, (struct sockaddr *)&client_address, &client_address_len);
        if (client_socket < 0)
        {
            perror("Error: Accept failed");
            continue;
        }

        // Allouer client_info pour le nouveau client
        client_info *client = malloc(sizeof(client_info));
        if (!client)
        {
            perror("Error: Memory allocation failed");
            close(client_socket);
            continue;
        }
        client->socket = client_socket;
        client->address = client_address;

        pthread_mutex_lock(&clients_mutex);
        if (client_count >= MAX_CLIENTS)
        {
            fprintf(stderr, "Error: Too many clients connected\n");
            close(client_socket);
            free(client);
        }
        else
        {
            clients[client_count++] = *client;
            pthread_t thread_id;
            if (pthread_create(&thread_id, NULL, client_handler, client) != 0)
            {
                perror("Error: Failed to create thread");
                close(client_socket);
                free(client);
                client_count--;
            }
            else
            {
                pthread_detach(thread_id); // Detach thread
            }
        }
        pthread_mutex_unlock(&clients_mutex);
    }

    close(server_socket);
    return 0;
}