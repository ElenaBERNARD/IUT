#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>

// Global variables for processes and message count
int message_count = 0;
int num_messages = 10;
pid_t pid_fils1, pid_fils2, pid_fils3;

// Signal handler for Fils1
void fils1() {
    printf("Montbéliard\n");
    fflush(stdout);  // Ensure immediate printing
    kill(getppid(), SIGUSR1);  // Send signal to the parent
}

// Signal handler for Fils2
void fils2() {
    printf("Belfort ");
    fflush(stdout);  // Ensure immediate printing
    kill(pid_fils1, SIGUSR1);  // Send signal to Fils1
}

// Signal handler for Fils3
void fils3() {
    printf("IUT ");
    fflush(stdout);  // Ensure immediate printing
    kill(pid_fils2, SIGUSR1);  // Send signal to Fils2
}

// Signal handler for the parent process
void pere() {
    message_count++;
    if (message_count < num_messages) {
        kill(pid_fils3, SIGUSR1);  // Send signal to Fils3 to start the next round
    } else {
        // After the required number of messages, terminate the child processes
        kill(pid_fils1, SIGKILL);
        kill(pid_fils2, SIGKILL);
        kill(pid_fils3, SIGKILL);

        // Wait for the child processes to finish
        wait(NULL);
        wait(NULL);
        wait(NULL);

        printf("Père: Nombre de messages atteint, fin du programme.\n");
        exit(0);
    }
}

// Function to set up a signal handler using sigaction
void set_signal_handler(int signum, void (*handler)(int)) {
    struct sigaction sa;
    sa.sa_handler = handler;
    sa.sa_flags = SA_RESTART;  // Restart system calls if interrupted by signal
    sigemptyset(&sa.sa_mask);  // No additional signals will be blocked during execution
    sigaction(signum, &sa, NULL);
}

int main() {
    // Create the child processes

    // Fils1
    pid_fils1 = fork();
    if (pid_fils1 < 0) {
        perror("Erreur lors de la création de Fils1");
        exit(1);
    }
    if (pid_fils1 == 0) {
        set_signal_handler(SIGUSR1, fils1);
        while (1) pause();  // Wait for signal
    }

    // Fils2
    pid_fils2 = fork();
    if (pid_fils2 < 0) {
        perror("Erreur lors de la création de Fils2");
        exit(1);
    }
    if (pid_fils2 == 0) {
        set_signal_handler(SIGUSR1, fils2);
        while (1) pause();  // Wait for signal
    }

    // Fils3
    pid_fils3 = fork();
    if (pid_fils3 < 0) {
        perror("Erreur lors de la création de Fils3");
        exit(1);
    }
    if (pid_fils3 == 0) {
        set_signal_handler(SIGUSR1, fils3);
        while (1) pause();  // Wait for signal
    }

    // Parent process: setup signal handler and send the first signal to Fils3
    set_signal_handler(SIGUSR1, pere);
    printf("Père prêt à envoyer des signaux (PID: %d)\n", getpid());

    // Start the process by sending the first signal to Fils3
    kill(pid_fils3, SIGUSR1);

    // Parent waits for signals
    while (1) pause();

    return 0;
}
