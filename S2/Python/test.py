import networkx as nx
import matplotlib.pyplot as plt
import numpy as np
# G = nx.Graph()
# G.add_node(1)
# G.add_nodes_from([2, 3, 4])
# G.add_edge(1,2)
# G.add_edges_from([(1, 3), (1, 4), (2, 3)])
# G.add_edge(1, 5) # Même si le noeud n’existe pas encore

# # Ceci n'est pas du code 
# # nx.draw(G, with_labels=True)
# # plt.show() # plt.savefig("graphe.png")

# G.add_edge(1, 2, weight=1)
# G.add_weighted_edges_from([(1, 2, 3), (2, 3, 4), (3, 4, 5)])

# # Ceci n'est pas du code 
# # G = nx.DiGraph()
# # G = nx.MultiGraph()
# # G = nx.MultiDiGraph()

# print(list(G.nodes))
# print(G.number_of_nodes())
# print(G.degree)

# def is_regular(g):
#     return len(set([g.degree(k) for k in g.nodes()])) == 1

# print(is_regular(G))


# def get_info(g):
#     print("Is regular :", is_regular(g))

# G = nx.Graph()
# G.add_nodes_from([1, 2, 3, 4, 5, 6])
# G.add_edges_from([(1, 2), (1, 3), (1, 5), (2, 3), (2, 4), (2, 5), (2, 6), (3, 4), (3, 5), (3, 6), (4, 5), (4, 6)])

# get_info(G)

# nx.draw(G, with_labels=True)
# plt.show() # plt.savefig("graphe.png")

def from_matrix(M):
    G=nx.Graph()
    for k in range(len(M)-1):
        for l in range(k, len(M)):
            if M[k,l]:
                G.add_edge(k, l)
    return G

def from_dict(dico):
    G=nx.Graph()
    for k in dico:
        for l in dico[k]:
            G.add_edge(k, l)
    return G

def list_of_parents_from_matrix(M, x):
    l=[]
    for k in range(len(M)):
        if M[x, k]:
            l.append(k)
    return l

adj_matrix_np = np.array([[0, 1, 1, 1, 0, 0],
              [1, 0, 1, 1, 1, 1],
              [1, 1, 0, 1, 1, 1],
              [1, 1, 1, 0, 1, 0],
              [0, 1, 1, 1, 0, 1],
              [0, 1, 1, 0, 1, 0]])

# for i in range(len(adj_matrix_np)):
#     print(i, " : ", list_of_parents_from_matrix(adj_matrix_np, i))

G = nx.from_numpy_array(adj_matrix_np)
nx.draw(G, nx.shell_layout(G), with_labels=True)
plt.show()

def lemme_poignees_main(G):
    return G.number_of_edges() == sum([G.degree(node) for node in list(G.nodes())]) / 2

print(lemme_poignees_main(G))