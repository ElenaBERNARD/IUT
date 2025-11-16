// gcc -g -Og -Wall -Wextra -o interception_signal_r-manuel interception_signal_r-manuel.c
// ./interception_signal_r-manuel
#include <stdio.h>
#include <signal.h>
#include <unistd.h>
#include <stdbool.h>

void signal_recu(int signum) {
    printf("Signal n°%d\n", signum);
    // Relancer signal 'de force'
    signal(signum, signal_recu);
}

int main() {
    for (int i = 1; i < _NSIG; ++i) {
        signal(i, signal_recu);
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
