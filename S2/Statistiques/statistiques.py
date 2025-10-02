def moyenne(liste, effectifs=None):
    if effectifs is None:
        effectifs = [1 for n in liste]
    if len(liste) != len(effectifs):
        print('Effectif et/ou valeur non conforme')
        return
    
    somme_effectifs = 0
    somme_valeurs = 0
    for n, c in zip(liste, effectifs):
        somme_valeurs += n*c
        somme_effectifs += c
    return somme_valeurs / somme_effectifs

def variance(liste, effectifs=None):
    if effectifs is None:
        effectifs = [1 for n in liste]
        
    m = moyenne(liste, effectifs)
    somme_variance = 0
    somme_effectifs = 0
    for n, c in zip(liste, effectifs):
        somme_variance += (n - m)**2 * c
        somme_effectifs += c
    return somme_variance / somme_effectifs

def ecart_type(liste, effectifs=None):
    return variance(liste, effectifs) ** 0.5

def q1(liste, effectifs=None):
    if effectifs is None:
        effectifs = [1 for n in liste]
    stop = 0
    for c in effectifs:
        stop += c
    stop = stop*0.25
    
    valeurs = [(n, c) for n, c in zip(liste, effectifs)]
    valeurs.sort()
    
    somme = 0
    for valeur in valeurs:
        somme += valeur[1]
        if somme >= stop:
            return valeur[0]
        
def q3(liste, effectifs=None):
    if effectifs is None:
        effectifs = [1 for n in liste]
    stop = 0
    for c in effectifs:
        stop += c
    stop = stop*0.75
    
    valeurs = [(n, c) for n, c in zip(liste, effectifs)]
    valeurs.sort()
    
    somme = 0
    for valeur in valeurs:
        somme += valeur[1]
        if somme >= stop:
            return valeur[0]
    
print(q1([1, 5, 9, 4, 2], [1, 5, 4, 3, 7]))