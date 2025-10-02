from math import sqrt

def isPrime(n):
    if n < 2:
        return False
    if n==2:
        return True
    if n%2 == 0:
        return False
    for i in range(3, int(sqrt(n))+2, 2):
        if n%i == 0:
            return False
    return True

def decompose(n):
    k = 2
    tab = []    
    while k*k <= n:
        if n%k:
            k+=1
        else:
            n //= k
            tab.append(k)
    if n > 1:
        tab.append(n)      
    return tab

print(isPrime(37975227936943673922808872755445627854565536638199))
print(decompose(1522605027922533360535618378132637429718068114961380688657908494580122963258952897654000350692006139))
    
# 1 125 899 906 842 723