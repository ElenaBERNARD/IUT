import numpy as np
from math import *
import matplotlib.pyplot as plt

def z(x):
    return exp(-((x-5.456454)**2)/2) + exp(-((x-58.34523)**2)/2)

def affiche(f, min, max, pas):

    x_values = []
    y_values = []
    x = min

    while x < max:
        x_values.append(x)
        y_values.append(f(x))
        x += pas

    plt.plot(x_values, y_values)

    plt.xlim(min, max)
    plt.title('Graphique de la fonction')
    plt.xlabel('abscisse')
    plt.ylabel('ordonnee')

    plt.grid(True)

    plt.show()

def zero(f, xmin, xmax):
    c = 0
    while(abs(xmin - xmax) > 1e-12):
        m = (xmin+xmax)/2
        if(f(m) == 0):
            return m
        elif(f(m)*f(xmin) > 0):
            xmin = m
        else:
            xmax = m
        c += 1
    print(f"Done in {c} itterations")
    print(m)
    return m


#affiche(f, -10, 10, 0.01)
#affiche(f, -1.5, 1.7, 0.001)
#Fonction polynomiale de degré 5, tend vers l'infini, exponentielle.

#affiche(g, -5, 5, 0.01)
#affiche(g, -10, 10, 0.01)
#affiche(g, -60, 60, 0.01)
#Fonction oscillatoire brrrrrrrrrrrrrrrrr

#affiche(h, -5, 5, 0.01)
#affiche(h, -10, 10, 0.01)
#affiche(h, -200, 200, 0.01)

#affiche(z, 0, 10, 0.01)
#affiche(z, 0, 70, 0.01)

def f(x):
    return x**2 - 4
#print(zero(f, 3, 6))
#print(zero(f, 0, 3))
#print(zero(f, -3, 3))

def g(x):
    return x**2 - 2
#print(zero(g, 1, 2))

def h(x):
    return x**3 - 4*x - 11
# test = zero(h, 0, 10)
# print(test, h(test))

def i(x):
    return x - cos(x)
# test = zero(i, 0, 10)
# print(test, i(test))
# print(cos(test))

# affiche(i, 0, 50, 0.01)

def derive(f, x):
    h = 0.00000001
    return (f(x+h) - f(x))/h

def cube(x):
    return x**3

# print(derive(cube, 2))

def extremumLocal(f, xmin, xmax):
    text = ", minimum found at :"
    c = 0
    if(derive(f, xmin) > derive(f, xmax)):
        xmin, xmax = xmax, xmin
        text = ", maximum found at :"
    while(abs(xmin - xmax) > 1e-12):
        m = (xmin+xmax)/2
        if(derive(f, m) == 0):
            print(f"Done in {c} itterations" + text)
            return m, f(m)
        elif(derive(f, m)*derive(f,xmin) > 0):
            xmin = m
        else:
            xmax = m
        c += 1
    print(f"Stopped after {c} itterations" + text)
    return m, f(m)

def test(x):
    return -x**2 - 4

# affiche(test, -1, 4, 0.01)
# print(extremumLocal(test, -1, 2.75))

# PREPA EXAM :

def fonction(x):
    return x**5 - 3*x**4 + 2*x**3 + 5*x**2 - 7*x + 2

zero(fonction, -1.5, -1)
zero(fonction, 0, 0.5)
zero(fonction, 0.7, 1.2)

print(extremumLocal(fonction, -0.5, 1))

affiche(fonction, -2, 3, 0.01)