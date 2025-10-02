#include <iostream>
using namespace std;

void swap1(int a, int b)
{
    int c = a;
    a = b;
    b = c;
}
void swap2(int *a, int *b)
{
    int c = *a;
    *a = *b;
    *b = c;
}
void swap3(int &a, int &b)
{
    int c = a;
    a = b;
    b = c;
}

int main()
{
    int a = 1, b = 2;
    cout << "a = " << a << ", b = " << b << endl;
    swap1(a, b);
    cout << "a = " << a << ", b = " << b << endl;
    swap2(&a, &b);
    cout << "a = " << a << ", b = " << b << endl;
    swap3(a, b);
    cout << "a = " << a << ", b = " << b << endl;
    return 0;
}

// On constate que les valeurs sont échangées uniquement avec la fonction swap2 et swap3.
// Cela s'explique car dans swap1, les valeurs de a et b sont passées par copie
// donc les valeurs de a et b dans la fonction swap1 sont des copies des valeurs de a et b dans la fonction main.