#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <pthread.h>

#define BUFFER_SIZE 1024

#define COLOR_RESET "\e[0m"
#define GRN "\e[1;32m"

void erreur(const char *message)
{
    perror(message);
    exit(EXIT_FAILURE);
}

// Thread function for receiving messages from the server
void *receive_messages(void *arg)
{
    int server_socket = *(int *)arg;
    char sender_info[INET_ADDRSTRLEN + 6];
    char buffer[BUFFER_SIZE];
    ssize_t bytes_received;

    while (1)
    {
        bytes_received = read(server_socket, sender_info, sizeof(sender_info) - 1);
        if (bytes_received > 0)
        {
            sender_info[bytes_received] = '\0'; // Null-terminate the received string
            printf(GRN "%s: " COLOR_RESET, sender_info);
        }
        else if (bytes_received == 0)
        {
            printf("Connection closed by server\n");
        }
        else
        {
            perror("read failed");
        }
        bytes_received = read(server_socket, buffer, sizeof(buffer) - 1);
        if (bytes_received > 0)
        {
            buffer[bytes_received] = '\0'; // Null-terminate the received string
            printf("%s\n", buffer);
        }
        else if (bytes_received == 0)
        {
            printf("Connection closed by server\n");
        }
        else
        {
            perror("read failed");
        }
    }

    close(server_socket);
    exit(EXIT_SUCCESS);
}

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
        fprintf(stderr, "Usage: %s server_ip server_port\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    char *server_ip = argv[1];
    int server_port = atoi(argv[2]);

    int server_socket = socket(AF_INET, SOCK_STREAM, 0);
    if (server_socket < 0)
    {
        erreur("Error: Socket creation failed");
    }

    struct sockaddr_in server_address = {
        .sin_family = AF_INET,
        .sin_port = htons(server_port)};

    if (inet_pton(AF_INET, server_ip, &server_address.sin_addr) <= 0)
    {
        close(server_socket);
        erreur("Error: Invalid server IP address");
    }

    if (connect(server_socket, (struct sockaddr *)&server_address, sizeof(server_address)) < 0)
    {
        close(server_socket);
        erreur("Error: Connection failed");
    }

    printf("Connected to server %s:%d\n", server_ip, server_port);

    pthread_t thread_id;
    if (pthread_create(&thread_id, NULL, receive_messages, &server_socket) != 0)
    {
        close(server_socket);
        erreur("Error: Failed to create thread");
    }

    char buffer[BUFFER_SIZE];
    while (fgets(buffer, sizeof(buffer), stdin) != NULL)
    {
        send(server_socket, buffer, strlen(buffer), 0);
    }

    close(server_socket);
    return 0;
}
