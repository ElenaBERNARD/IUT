import matplotlib as mp
from math import *
print("\n\nSuite (u1) : ")
# Suite (u1)
# u(0) = 2.56453
# u(n + 1) = 0,9972u(n) + 2123.56

# Calcule fait à l'aide d'une calculatrice : 
# u(1) = 2126.11711
# u(2) = 4243.72399
# u(3) = 6355.40156
# u(4) = 8461.16644

def u1_iteratif(n) :
    u = 2.56453
    for i in range(n) :
        u = u*0.9972 + 2123.56
    return u


def u1_recursif(n, u = 2.56453) :
    
    if n==0 :
        return u
    else : 
        return 0.9972 * u1_recursif(n-1) + 2123.56

print(f"Calcule de u(1) avec la méthode itératif : {u1_iteratif(1)}, avec la méthode récursive {u1_recursif(1)}")
print(f"Calcule de u(2) avec la méthode itératif : {u1_iteratif(2)}, avec la méthode récursive {u1_recursif(2)}")
print(f"Calcule de u(3) avec la méthode itératif : {u1_iteratif(3)}, avec la méthode récursive {u1_recursif(3)}")
print(f"Calcule de u(4) avec la méthode itératif : {u1_iteratif(4)}, avec la méthode récursive {u1_recursif(4)}")

print("Test des valeurs dans l'interval [0,500] avec un pas de 5")

for i in range(0,501,5) :
    print(f"u{i} = {u1_iteratif(i)}")

# Calcule de la limite numérique de (u1)

# un1,un, n  = 1, 2, 0
# while un1 != un :
#     un = u1_iteratif(n)
#     un1 = u1_iteratif(n+1)
#     n+=1
# limite1 = u1_iteratif(n)
# print(f"Conjecture :\nLa suite (u1) semble être strictement croissante, concave et convergé.\nLa limite numérique de (u1) est {limite1} en n = {n}")

# Conjecture : 
# La suite (u1) semble être strictement croissante, concave et convergé.

# Après calcule :
# La limite numérique de (u1) est 758414.2857142782 en n = 11097


print("\n\nSuite (u2) : ")
# Suite (u2)
# u(0) = 2.56453
# u(n + 1) = 0,9972u(n) + n^2

# Calcule fait à l'aide d'une calculatrice : 
# u(1) = 2.55712.
# u(2) = 3.54996
# u(3) = 7.54002
# u(4) = 16.5189

def u2_iteratif(n) :
    u = 2.56453
    for i in range(n) :
        u = u*0.9972 + (i)**2
    return u

def u2_recursif(n, u = 2.56453) :
    
    if n==0 :
        return u
    else : 
        return 0.9972 * u2_recursif(n-1) + (n-1)**2

print(f"Calcule de u(1) avec la méthode itératif : {u2_iteratif(1)}, avec la méthode récursive {u2_recursif(1)}")
print(f"Calcule de u(2) avec la méthode itératif : {u2_iteratif(2)}, avec la méthode récursive {u2_recursif(2)}")
print(f"Calcule de u(3) avec la méthode itératif : {u2_iteratif(3)}, avec la méthode récursive {u2_recursif(3)}")
print(f"Calcule de u(4) avec la méthode itératif : {u2_iteratif(4)}, avec la méthode récursive {u2_recursif(4)}")

print("Test des valeurs dans l'interval [0,500] avec un pas de 5")
for i in range(0,501,5) :
    print(f"u{i} = {u2_iteratif(i)}")
    print(f"Difference entre les deux terme qui se suive : {u2_iteratif(i) - u2_iteratif(i-1)}")

# Conjecture : 
# La suite (u1) semble être strictement croissante et divergé vers plus l'infinie. 


print("\n\nSuite (u2) : ")
# Suite (u2)
# u(0) = 0
# u(1) = 1
# u(n + 2) = u(0) + u(1)

# Calcule fait à l'aide d'une calculatrice : 
# u(2) = 1
# u(3) = 2
# u(4) = 3
# u(5) = 5
# u(6) = 8
# u(7) = 13


def u3_iteratif(n) :
    if n <= 1 : return n

    a,b = 0,1
    for i in range(n-1) :
        c = a + b
        a = b
        b = c
    return c

def u3_recursif(n) :
    if n<=1 : return n
    else : return u3_recursif(n-1) + u3_recursif(n-2)

print(f"Calcule de u(2) avec la méthode itératif : {u3_iteratif(2)}, avec la méthode récursive {u3_recursif(2)}")
print(f"Calcule de u(3) avec la méthode itératif : {u3_iteratif(3)}, avec la méthode récursive {u3_recursif(3)}")
print(f"Calcule de u(4) avec la méthode itératif : {u3_iteratif(4)}, avec la méthode récursive {u3_recursif(4)}")
print(f"Calcule de u(5) avec la méthode itératif : {u3_iteratif(5)}, avec la méthode récursive {u3_recursif(5)}")
print(f"Calcule de u(6) avec la méthode itératif : {u3_iteratif(6)}, avec la méthode récursive {u3_recursif(6)}")
print(f"Calcule de u(7) avec la méthode itératif : {u3_iteratif(7)}, avec la méthode récursive {u3_recursif(7)}")

print("Test des valeurs dans l'interval [0,100]")
for i in range(101) :
    print(f"u({i}) = {u3_iteratif(i)}")

# La suite semble être strictement croissante en accelerant et divergente vers l'infin positif.


#Résulat à la calculatrice
#u(0), v(0) = 2, 1
#u(1), v(1) = 1.5, 1.33
#u(2), v(2) = 1.41, 1.41
#u(3), v(3) = 1.41, 1.41
def uv_iteratif(n):
    a, b = 2, 1
    for i in range(n):
        c = a
        a = (a+b)/2
        b = (2*c*b)/(c+b)
    return (a, b)

def uv_recursif(n):
    if n < 1: 
        return (2, 1)
    uv = uv_recursif(n-1)
    return ((uv[0] + uv[1])/2, (2*uv[0]*uv[1])/(uv[0]+uv[1]))

#La suite u(n) semble être strictement décroissante et convergente en un point (environ 1.414213562373095), stabilisé au rang 5
#La suite v(n) semble être strictement croissante et convergente en un point (environ 1.414213562373095), stabilisé au rang 5
#La limite est probablement sqrt(2)

#Theoreme des suites adjacentes : (u) croissante et (v) decroissante et (u)-(v) converge vers 0, alors (u) et (v) sont convergentes
for i in range(101):
    print(f"u({i}) = {uv_iteratif(i)[0]} ; v({i}) = {uv_iteratif(i)[1]}")
    

#Approximation du nombre d'or
def nb_or(n):
    return u3_iteratif(n+1)/u3_iteratif(n)

for i in range(2, 100):
    print(f"Rang {i} : nb d'or aprox = {nb_or(i)}")

#La suite converge relativement rapidement, car elle se stabilise au 40eme rang à 1.618033988749895


#Approxiamtion de pi
def aprox_pi(n):
    s = 1
    for i in range(2, n):
        s += 1/i**2
    return sqrt(s*6)
    
print("\nApproximation de pi (lentement)")
for i in range(2, 100):
    print(f"Rang {i} : pi aprox = {aprox_pi(i)}")
    
#La suite converge tres lentement, car elle ne se stabilise pas dans les 100 premiers rangs (6 decimale après 100 000 000 d'itération)

#Meilleure approximation de pi
def aprox_pi2(n):
    #2*sqrt(2)/9801
    s = 0
    for i in range(n):
        s += (factorial(4*i)*(1103 + 26390*i))/((factorial(i)**4)*396**(4*i))
    return 1/(2*sqrt(2)/9801*s)

print("\nApproximation de pi (rapidement)")
for i in range(2, 100):
    print(f"Rang {i} : pi aprox = {aprox_pi2(i)}")

#La suite converge extremement rapidement, car elle se stabilise au 3eme rang 3.141592653589793