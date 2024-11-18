// gcc -g -Og -Wall -Wextra -o 100_fils 100_fils.c
// ./100_fils
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    for (int i = 0; i < 100; i++) {
        pid_t pid = fork(); 

        switch (pid)
        {
        case -1:
            perror("Erreur lors de la creation du processus fils");
            return 1;
        case 0:
            printf("Fils n°%d : PID = %d, PPID = %d\n", i+1, getpid(), getppid());
            return 0;
        default:
            continue;
            break;
        }
    }

    for (int i = 0; i < 100; i++) {
        wait(NULL);
    }

    return 0;
}
