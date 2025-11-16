// gcc -g -Og -Wall -Wextra -o interception_sigaction_r-auto interception_sigaction_r-auto.c
// ./interception_sigaction_r-auto
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <stdbool.h>

void signal_recu(int signum) {
    printf("Signal n°%d\n", signum);
}

int main() {
    struct sigaction sa;
    sa.sa_handler = signal_recu;
    // Creer un ensemble de signaux vides pour le masque de sigaction
    sigemptyset(&sa.sa_mask);
    sa.sa_flags = 0;

    // Interception de tous les signaux de 1 à (_NSIG - 1)
    for (int i = 1; i < _NSIG; ++i) {
        sigaction(i, &sa, NULL);
    }

    // Cette boucle ne permet que d'attendre 10s et s'arrete a la reception d'un signal
    // bool wait_for_signals = true;
    // while (wait_for_signals) {
    //     sleep(10);
    //     wait_for_signals = false;
    // }

    // Cette boucle est infinie et permet d'attraper tous les signaux
    // Solution a l'arret du programme, trouvee sur StackOverflow
    while (1) {
        pause();
    }

    return 0;
}
