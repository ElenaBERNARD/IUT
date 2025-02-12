import numpy as np

A = [
    [1 , -1, 2], 
    [10, 6, -8], 
    [-2, -1, 5]]
 
mat_A = np.array(A)

v1 = np.array([[1],[2], [2]])
v2 = np.array([[1], [-1], [1]])
v3 = np.array([[1], [-2], [1]])

# print(np.dot(mat_A, v1))
# print(np.dot(mat_A, v2))
# print(np.dot(mat_A, v3))

P = np.array([
    [1, 1, 1], 
    [2, -1, -2], 
    [2, 1, 1]
])
D = np.array([
    [3, 0, 0],
    [0, 4, 0],
    [0, 0, 5]
])

# print(np.dot(np.dot(P, D), np.linalg.inv(P)))

valp, vecp = np.linalg.eig(mat_A)

# print(valp)
# print(vecp)

mat_B = np.array([
    [0, 1, 0],
    [0, -1, 2],
    [-1, -1, 3]
])

vb1 = np.array([[1], [1], [1]])
vb2 = np.array([[1], [-1], [0]])
vb3 = np.array([[1], [2], [3]])

# print(np.dot(mat_B, vb1))
# print(np.dot(mat_B, vb2))
# print(np.dot(mat_B, vb3))

def mbb(A):
    valp, vecp = np.linalg.eig(A)
    D = np.zeros(A.shape)
    for i in range(len(valp)):
        D[i][i] = valp[i]
    return D

hier = np.array([
    [3, 0, -1],
    [2, 4, 2],
    [-1, 0, 3]
])

# print(mbb(hier))
# print(np.linalg.eig(hier))

mat_c = np.array([
    [8, -2, -2],
    [3, 1, -3],
    [7, -5, -1]
])

cv1 = np.array([[1], [2], [3]])
cv2 = np.array([[0], [1], [-1]])
cv3 = np.array([[1], [0], [1]])

# print(np.dot(mat_c, cv1))
# print(np.dot(mat_c, cv2))
# print(np.dot(mat_c, cv3))

mat_d = np.array([
    [3, -1, 1],
    [-2, 4, 2],
    [-1, 1, 5]
])

print(mbb(mat_d))

dv1 = np.array([[1], [1], [0]])
dv2 = np.array([[1], [0], [1]])
dv3 = np.array([[0], [1], [1]])

print(np.dot(mat_d, dv1))
print(np.dot(mat_d, dv2))
print(np.dot(mat_d, dv3))
