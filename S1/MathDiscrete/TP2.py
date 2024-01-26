##################################################
########## dernierChiffre(n)
##################################################
def dernierChiffre(n):
    return n%10

# assert(dernierChiffre(7)) == 7
# assert(dernierChiffre(6789049876)) == 6
# print(dernierChiffre(26**53294))

##################################################
########## deuxDerniersChiffres(n)
##################################################
def deuxDernierChiffre(n):
    return n%100

# assert(deuxDernierChiffre(7)) == 7
# assert(deuxDernierChiffre(6789049876)) == 76
# print(deuxDernierChiffre(26**53294))

##################################################
########## derniersChiffres(n, nbre)
##################################################
def derniersChiffres(n, nbre):
    return n%10**nbre

# assert(derniersChiffres(7, 4)) == 7
# assert(derniersChiffres(6789049876, 5)) == 49876
# print(derniersChiffres(26**53294, 12))

##################################################
########## nombreChiffres(n)
##################################################
def nombreChiffresRec(n):
    if abs(n) < 10:
        return 1
    return nombreChiffres(n//10) + 1

##################################################
def nombreChiffresIte(n):
    compteur = 0
    while(abs(n) > 1):
        compteur += 1
        n //= 10
    return compteur

# assert(nombreChiffresIte(45)) == 2
# assert(nombreChiffresIte(-189304)) == 6

##################################################
########## premierChiffre(n)
##################################################
def premierChiffre(n):
    while n >= 10:
        n//=10
    return n

assert(premierChiffre(45)) == 4
assert(premierChiffre(189304)) == 1

##################################################
########## premiersChiffres(n)
##################################################
def premiersChiffres(n, nbre):
    while n >= 10**nbre:
        n//=10
    return n

assert(premiersChiffres(45, 1)) == 4
assert(premiersChiffres(189304, 3)) == 189
# print(premiersChiffres(26**53294, 100))

##################################################
########## chiffres(n)
##################################################
def chiffres(n, debut, fin):
    return premiersChiffres(n, fin)%10**(fin - debut + 1)

print(chiffres(123456789, 3, 5))
print(chiffres(123456789, 4, 9))
