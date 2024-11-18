#include <stdio.h>

int copie(FILE *entree, FILE *sortie)
{
    if (entree == NULL || sortie == NULL)
    {
        return -1;
    }

    size_t copie = 0;
    size_t nbBlocs;
    char buffer[4096]; // Un buffer de 4 Ko pour la copie par blocs

    // Boucle de copie par bloc :
    // Ici, on copie blocs de 8 octets dans buffer
    // buffer           -> cible de la copie
    // 1                -> Taille d'un bloc en octets, ici 1 octet par blocs
    // sizeof(buffer)   -> Nombre de bloc a copier, ici equivalent au nombre d'octet dans buffer
    // entree           -> fichier a copier
    // blocSize         -> nombre de bloc lu dans entree
    while ((nbBlocs = fread(buffer, 1, sizeof(buffer), entree)) > 0)
    {
        if (fwrite(buffer, 1, nbBlocs, sortie) != nbBlocs)
        {
            return -1; // Erreur d'écriture
        }
        copie += nbBlocs;
    }

    return (int)copie;
}

int main()
{
    char nomFichierEntree[1024];
    printf("Entrer le nom du fichier a copier (avec l'extension du fichier !) : ");
    gets(nomFichierEntree);

    char nomFichierSortie[1024];
    printf("Entrer le nom du fichier copie (avec l'extension du fichier !) : ");
    gets(nomFichierSortie);

    FILE *fichierEntree = fopen(nomFichierEntree, "rb");
    FILE *fichierSortie = fopen(nomFichierSortie, "wb");

    if (fichierEntree == NULL || fichierSortie == NULL)
    {
        perror("Erreur de l'ouverture des fichiers");
        return 1;
    }

    int nbOctetsCopies = copie(fichierEntree, fichierSortie);
    if (nbOctetsCopies == -1)
    {
        printf("Erreur de copie du fichier.\n");
    }
    else
    {
        printf("Nombre d'octets copiés : %d\n", nbOctetsCopies);
    }

    fclose(fichierEntree);
    fclose(fichierSortie);
    return 0;
}
