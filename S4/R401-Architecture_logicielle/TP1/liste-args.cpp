#include <iostream>
#include <vector>
using namespace std;

int main(int argc, char *argv[])
{
    if(argc <= 2)
    {
        cout << "Usage: " << argv[0] << " <arg1> <arg2> <arg3> ..." << endl;
        return 1;
    }

    cout << "Number of arguments : " << argc -1 << endl;
    vector<string> args(argv + 1, argv + argc);
    for(const string $item: args)
    {
        cout << $item << " ";
    }
    cout << endl;
    return 0;
}