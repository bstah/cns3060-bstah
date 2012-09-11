#include <iostream>

using namespace std;

int main(int argc, char* argv[])// char* env[])
{
cout << "Brian Staheli" << endl << "CS3060" << endl;
for (int i = 1; i < argc; i++)
{
cout << argv[i] << endl;
}
return 0;
}
