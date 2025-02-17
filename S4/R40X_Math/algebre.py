import numpy as np

A = np.array(
    [[3, 2, 8, -1],
    [8, 4, -17, 3],
    [6, -2 ,7, 10],
    [-5, 3, 9, 7]]
)
B = np.array(
    [5, 8, 19 ,43]
)

print(np.dot(np.linalg.inv(A), B))