#include <iostream>
using namespace std;

int main(int argc, char *argv[])
{
    if(argc != 2)
    {
        cout << "Usage: " << argv[0] << " <name>" << endl;
        return 1;
    }
    string name = argv[1];
    cout << "Hello " << name << " !" << endl;
    return 0;
}