// gcc -g -Og -Wall -Wextra -o 100_petits_fils 100_petits_fils.c
// ./100_petits_fils
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main()
{
    pid_t pid;
    int n = 1;

    while (n <= 100)
    {
        pid = fork();

        switch (pid)
        {
        case -1:
            perror("Erreur lors de la création du processus");
            return 1;

        case 0:
            printf("Processus n°%d: PID = %d, PPID = %d\n", n, getpid(), getppid());
            n++;
            break;

        default:
            wait(NULL);
            return 0;
        }
    }

    return 0;
}
