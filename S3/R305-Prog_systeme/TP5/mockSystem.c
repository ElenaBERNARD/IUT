// gcc -g -Og -Wall -Wextra -o mockSystem mockSystem.c
// ./mockSystem
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/resource.h>
#include <sys/wait.h>

int mon_system(const char *command) {
    // Si command vaut NULL on retourne une valeur différente de zéro
    if (command == NULL) {
        return 1; 
    }

    pid_t pid;
    int status;

    pid = fork();
    
    // Si le processus fils n'a pas pu être cree on retourne -1
    if (pid < 0) {
        return -1;
    }
    
    // pid == 0 si dans fils
    if (pid == 0) {
        // Exécution de la commande via un shell
        execl("/bin/sh", "sh", "-c", command, (char *) NULL);

        // Si execl échoue on fait appel a _exit127
        _exit(127);
    } 
    // else si dans parent
    else {
        // Attente du fils
        do {
            // Attendre la terminaison du processus fils
            if (waitpid(pid, &status, 0) == -1) {
                status = -1;
                break;
            }
        } while (!WIFEXITED(status) && !WIFSIGNALED(status));
    }
    return status;
}

// APRES CES COMMENTAIRES :
// CODE DE VERIFICATION DISPONIBLE SUR COURS-INFO
void verification_system(const char *commande)
{
    int a = system(commande);
    int b = mon_system(commande);
    fprintf(stderr, "%s: \"%s\" (%d, %d)\n",
            a == b ? "OK  " : "FAIL", commande ? commande : "(null)", a, b);
}

void verification_system_limit(int resource, int valeur, const char *commande)
{
    struct rlimit oldrl, newrl;
    if (getrlimit(resource, &oldrl) == -1) {
        perror("getrlimit");
        return;
    }
    newrl = oldrl;
    newrl.rlim_cur = valeur;
    if (setrlimit(resource, &newrl) == -1) {
        perror("setrlimit");
        return;
    }
    verification_system(commande);
    setrlimit(resource, &oldrl);
}

int main(int argc, char *argv[])
{
    const struct rlimit zerorl = {0, 0};
    if (setrlimit(RLIMIT_CORE, &zerorl) == -1)
        perror("setrlimit(RLIMIT_CORE)");

    if (argc >= 2) {
        for (int i = 1; i < argc; i++)
            verification_system(argv[i]);
    } else {
        const char *cmds[] = {
            "",                       /* empty command */
            "true",                   /* successful command */
            "false",                  /* failing command */
            "ls / > /dev/null",       /* another command */
            "exec 2>/dev/null; plop", /* non-existent command */
            "kill -HUP $$",           /* killed by SIGHUP */
            "kill -INT $$",           /* killed by SIGINT */
            "kill -QUIT $$",          /* killed by SIGQUIT */
            "kill -INT $PPID",        /* send SIGINT to main process */
            "kill -QUIT $PPID",       /* send SIGQUIT to main process */
            NULL                      /* NULL command */
        };

        for (int i = 0; i == 0 || cmds[i - 1] != NULL; i++) {
            verification_system(cmds[i]);
        }
        verification_system_limit(RLIMIT_NPROC, 0, ": failed fork");
        verification_system_limit(RLIMIT_AS, 0, ": failed exec");
    }
}