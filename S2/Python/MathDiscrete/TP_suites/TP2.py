from math import *
import matplotlib.pyplot as plt

def fu(x):
    return 0.5*x+2

def gu(x):
    return 3.56*x*(1-x)

def fv(x):
    return cos(x)

def fw(x):
    return -x**2 + 4

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


# convergence(gu, 0.5, 200, 0, 1, 0.01)

# convergence(fv, 0.6, 100, 0, 1, 0.01)
# Si u0 < 0.7, convergence par oscillations
# convergence(fv, 1.2, 100, 0, 1, 0.01)
# Si u0 > 0.7, convergence par oscillations

convergence(fw, 3, 2, -5, 5, 0.1)

