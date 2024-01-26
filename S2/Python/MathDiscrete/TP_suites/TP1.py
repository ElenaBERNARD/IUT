from math import *

def u1_iteratif(n):
    res = 2.56453
    for _ in range(n):
        res = res * 0.9972 + 2123.56
    return res

def u1_recursif(n):
    if n <= 0:
        return 2.56453
    return u1_recursif(n-1) * 0.9972 + 2123.56

# TEST :
# for i in range(4):
#     print(u1_iteratif(i), u1_recursif(i), i)
# Meme valeur qu'avec WolframAlpha

# Execution :
# for i in range(1, 12000, 10):
#     print(u1_iteratif(i), i)

# La suite u1 converge car forme q * un + r
# Or, 0 <= q < 1
# Par le calcul, lim(un) = 758414,2857142782
# Limite atteinte a n >= 11100

def u2_iteratif(n):
    res = 2.56453
    for i in range(1, n+1):
        res = res * 0.9972 + (i-1)**2
    return res

def u2_recursif(n):
    if n <= 0:
        return 2.56453
    return u2_recursif(n-1) * 0.9972 + (n-1)**2

# TEST :
# for i in range(4):
#     print(u2_iteratif(i), u2_recursif(i), i)
# Meme valeur qu'avec WolframAlpha

# Execution :
# for i in range(1, 1000000, 1000):
#     print(u2_iteratif(i), i)

# La suite u2 diverge et tend vers l'infini positif
# Pas de stabilisation

def u3_iteratif(n):
    if n <= 0:
        return 0
    temp = 0
    u0 = 0
    u1 = 1
    for _ in range(1, n):
        temp = u0
        u0 = u1
        u1 += temp
    return u1

def u3_recursif(n):
    if n <= 0:
        return 0
    if n == 1:
        return 1
    return u3_recursif(n-1) + u3_recursif(n-2)

# TEST :
# for i in range(10):
#     print(u3_iteratif(i), u3_recursif(i), i)
# Valeurs calcule a la main

# Execution :
# for i in range(100):
#     print(u3_iteratif(i), i)


# La suite u3 est strictement croissante et tend vers l'infini positif
# Pas de stabilisation

def u4(n):
    if n <= 0:
        return 7544279, 1
    temp = u4(n-1)
    un = temp[0]
    vn = temp[1]
    return (un+vn)/2, 2*un*vn/(un+vn)

# for i in range(100):
#     print(u4(i), i)

# Stabilisation a 5
# Racine de u0*v0, 
# Extraction de racine, voir page wikipedia
# Methode des suites adjacentes   
# Moyenne harmonique

##### 2. CONVERGENCES

# prev = 0
# new = 1
# i=3
# while(prev != new):
#     prev = new
#     new = u3_iteratif(i)/u3_iteratif(i-1)
#     print(i, " : ", new)
#     i+=1

def somme_inverse():
    s = 0
    prev = 0
    new = 1
    i=1
    while(prev != new or i < 1000000):
        prev = new
        new = sqrt(s*6)
        print(i, " : ", new)
        s += 1/i**2
        i+=1

# somme_inverse()

def convergence_pi():
    s = 0
    i = 0
    prev = 0
    new = 1
    while(prev != new or i < 10):
        prev = new
        s += (factorial(4*i)*(1103+26390*i)) / (((factorial(i))**4)*(396**(4*i)))
        new = 1/((2*sqrt(2)/9801)*s)
        print(i, " : ", new)
        i+=1

convergence_pi()

print("\n")

def convergence_pi_mieux():
    s = 0
    i = 0
    prev = 0
    new = 1
    while(prev != new or i < 10):
        prev = new
        s += (((-1)**i)*(factorial(6*i)) * (13591409+545140134*i)) / (factorial(3*i)*(factorial(i)**3)*(640320**(3*i+3/2)))
        new = 1/(12*s)
        print(i, " : ", new)
        i+=1
        
convergence_pi_mieux()