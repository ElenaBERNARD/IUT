#include <stdio.h>
#include <math.h>
#include <stdbool.h>

int mois[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

struct Date
{
    int jour;
    int mois;
    int annee;
};

bool estBissextile(int a)
{
    return ((a % 4 == 0 && !a % 100 == 0) || (a % 400 == 0));
};

struct Date lendemain(struct Date d)
{
    struct Date newDate;
    newDate.jour = d.jour + 1;
    newDate.mois = d.mois;
    newDate.annee = d.annee;

    if (!(estBissextile(newDate.annee) && newDate.mois == 2 && newDate.jour == 29))
        if (newDate.jour > mois[newDate.mois - 1])
        {
            newDate.jour = 1;
            newDate.mois++;
            if (newDate.mois > 12)
            {
                newDate.mois = 1;
                newDate.annee++;
            }
        }
        else
        {
            newDate.jour++;
        }
    return newDate;
};

struct Date veille(struct Date d)
{
    struct Date newDate;
    newDate.jour = d.jour - 1;
    newDate.mois = d.mois;
    newDate.annee = d.annee;

    if (!(estBissextile(newDate.annee) && newDate.mois == 3 && newDate.jour == 0))
        if (newDate.jour < 0)
        {
            newDate.mois--;
            if (newDate.mois < 0)
            {
                newDate.mois = 12;
                newDate.annee--;
            }
            newDate.jour = mois[newDate.mois - 1];
        }
        else
        {
            printf("caa");
            newDate.jour = 29;
            newDate.mois--;
        }
    return newDate;
};

int main()
{
    struct Date d;
    d.jour = 28;
    d.mois = 2;
    d.annee = 2024;

    printf("%i\n", estBissextile(2024));

    struct Date newDate = lendemain(d);

    printf("%i/%i/%i\n", newDate.jour, newDate.mois, newDate.annee);

    struct Date newDate2 = lendemain(newDate);

    printf("%i/%i/%i\n", newDate2.jour, newDate2.mois, newDate2.annee);

    newDate = veille(newDate2);

    printf("%i/%i/%i\n", newDate.jour, newDate.mois, newDate.annee);

    newDate.jour = 31;
    newDate.mois = 12;
    newDate.annee = 2024;
    
    newDate2 = lendemain(newDate);

    printf("%i/%i/%i\n", newDate2.jour, newDate2.mois, newDate2.annee);


    newDate2.jour = 1;
    newDate2.mois = 1;
    newDate2.annee = 2024;

    newDate = veille(newDate2);

    printf("%i/%i/%i\n", newDate.jour, newDate.mois, newDate.annee);


    return 0;
}
