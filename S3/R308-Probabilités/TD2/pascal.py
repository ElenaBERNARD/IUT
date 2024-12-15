import time
from math import comb
def pascal(n):
    triangle = [[1]]
    for i in range(n):
        triangle.append([1])
        for j in range(i):
            triangle[i].append(triangle[i-1][j] + triangle[i-1][j+1])
        triangle[i].append(1)

def pascalComprehension(p):
    triangle = [[comb(n, k) for k in range(n+1)] for n in range(p+1)]



startComprehension = time.time()
pascalComprehension(1000)
endComprehension = time.time()

startPascal = time.time()
pascal(1000)
endPascal = time.time()



print("Pascal :", endPascal-startPascal)
print("Comprehension :", endComprehension-startComprehension)
