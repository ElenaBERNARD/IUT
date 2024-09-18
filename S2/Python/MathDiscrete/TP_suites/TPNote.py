from math import *
import numpy as np
import matplotlib.pyplot as plt

def convergence(f, u0, rang, min, max, pas):
    points_x = []
    points_y = []
    index = min
    while(index < max):
        points_x.append(index)
        points_y.append(f(index))
        index += pas
    plt.plot(points_x, points_x)
    plt.plot(points_x, points_y)
    
    points_x = [u0, f(u0)]
    points_y = [f(u0), f(u0)]
    for _ in range(rang):
        un = f(u0)
        points_x.append(u0)
        points_y.append(un)
        points_x.append(un)
        points_y.append(un)
        u0=un
    
    plt.plot(points_x, points_y)
       
    plt.grid(True)
    plt.xlabel("n")
    plt.ylabel("u(n)")
    plt.show()

def graphique(u, nmin, nmax, pas):
    pointsX = []
    pointsY = []
    for i in range(nmin, nmax, pas):
        pointsX.append(i)
        pointsY.append(u(i))
    plt.scatter(pointsX, pointsY)
    plt.grid(True)
    plt.xlabel("n")
    plt.ylabel("u(n)")
    plt.show()
    
def graphiquemult(l, nmin, nmax, pas):
    compteur=0
    for u in l:
        pointsx = []
        pointsy = []
        for i in range(nmin, nmax, pas):
            pointsx.append(i)
            pointsy.append(u(i))
        plt.scatter(pointsx, pointsy, marker='+', label=f"Suite u{compteur}")
        compteur+=1
    plt.legend()
    plt.grid(True)
    plt.xlabel("n")
    plt.ylabel("u(n)")
    plt.show()

def graphiquemultu(l, nmin, nmax, pas):
    for u in l:
        pointsx = []
        pointsy = []
        for i in range(nmin, nmax, pas):
            pointsx.append(i)
            pointsy.append(u[0](i,u[1]))
        plt.scatter(pointsx, pointsy, marker='+', label=f"Suite r={u[1]}")
    plt.legend()
    plt.grid(True)
    plt.xlabel("n")
    plt.ylabel("u(n)")
    plt.show()

def derive(f, x):
    h = 0.00000001
    return (f(x+h) - f(x))/h

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

def uiterrative(n):
    u0 = 1
    for _ in range(n):
        u0 = 3/((u0**2) + 1)
    return u0

# print(uiterrative(0))
# print(uiterrative(1))
# print(uiterrative(2))
# print(uiterrative(3))

# graphique(uiterrative, 0, 100, 1)
# graphique(uiterrative, 10000, 10100, 1)

# compteur = 10
# while(uiterrative(compteur-2) != uiterrative(compteur)):
#     compteur += 2
# print("Rang de stavilisation : ", compteur)
# print("Valeur 1 : ", uiterrative(compteur))
# print("Valeur 2 : ", uiterrative(compteur-1))


##############
# EXERCICE 2 #
##############

def u1(n):
    u0 = 1
    for _ in range(n):
        u0 = 3/((u0**2) + 1)
    return u0

def uMoins1(n):
    u0 = -1
    for _ in range(n):
        u0 = 3/((u0**2) + 1)
    return u0

def uZero(n):
    u0 = 0
    for _ in range(n):
        u0 = 3/((u0**2) + 1)
    return u0

def uDeux(n):
    u0 = 2
    for _ in range(n):
        u0 = 3/((u0**2) + 1)
    return u0

def uDix(n):
    u0 = 10
    for _ in range(n):
        u0 = 3/((u0**2) + 1)
    return u0

graphiquemult([u1, uMoins1, uZero, uDeux, uDix], 0, 100, 1)

def f(x):
    return x**5 - 6.38238*x**4 - 2486.42*x**3 + 15946.3*x**2 - 33945.6*x + 24072.5

