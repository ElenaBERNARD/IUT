#include <iostream>
class C
{
    public:
    friend std::ostream &operator<<(std::ostream &os, const C &c);
};
void display_c(const C &c)
{
    std::cout << c << std::endl;
}
void display_int(int v)
{
    std::cout << v << std::endl;
}
void set_c(C *c)
{
    *c = C{};
}
void set_int(int *i)
{
    *i = 42;
}
int get_int(int i)
{
    return i + 1;
}
C *make_c()
{
    return new C{};
}
int main()
{
    C *c1 = make_c(); // variable name is c1
    C c2{};
    display_c(*c1); // with c1
    display_c(c2); // with c2
    int i1 = 55;
    int i = 25;
    int *i2 = &i;                          // shall get memory for one int and set it to 25
    display_int(i1);                       // for i1
    display_int(*i2);                       // for i2
    set_int(&i1);                           // for i1
    set_int(i2);                           // for i2
    std::cout << get_int(i1) << std::endl; // for i1
    std::cout << get_int(*i2) << std::endl; // for i2
    set_c(c1);                             // for c1
    set_c(&c2);                             // for c2
    return 0;
}
std::ostream &operator<<(std::ostream &os, const C &c)
{
    os << "I'm a C";
    return os;
}