import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from xml.dom import minidom
from svg.path import parse_path

# ====== PARAMÈTRES À MODIFIER ======
input_path = "cat.svg"  # Chemin du fichier SVG (vide = forme de test)
n_coeffs = 100   # Nombre de vecteurs de Fourier
duration = 10    # Durée d'un cycle complet (secondes)
fps = 60         # Images par seconde
sample_points = 2000  # Nombre de points à échantillonner sur le chemin
# ===================================

def generate_test_shape():
    """Génère une forme de test (cœur) si aucun SVG n'est fourni."""
    t = np.linspace(0, 2*np.pi, 200)
    x = 16 * np.sin(t)**3
    y = 13 * np.cos(t) - 5 * np.cos(2*t) - 2 * np.cos(3*t) - np.cos(4*t)
    return x + 1j * y

def extract_paths_from_svg(svg_path):
    """Extrait tous les chemins d'un fichier SVG (paths, lines, polylines, polygons)."""
    doc = minidom.parse(svg_path)
    path_strings = []
    
    # Extraire les éléments <path>
    for path in doc.getElementsByTagName('path'):
        d = path.getAttribute('d')
        if d:
            path_strings.append(d)
    
    # Extraire les <line>
    for line in doc.getElementsByTagName('line'):
        x1 = float(line.getAttribute('x1') or 0)
        y1 = float(line.getAttribute('y1') or 0)
        x2 = float(line.getAttribute('x2') or 0)
        y2 = float(line.getAttribute('y2') or 0)
        path_strings.append(f"M {x1},{y1} L {x2},{y2}")
    
    # Extraire les <polyline>
    for polyline in doc.getElementsByTagName('polyline'):
        points = polyline.getAttribute('points')
        if points:
            points_list = points.strip().split()
            if points_list:
                path_d = f"M {points_list[0]}"
                for point in points_list[1:]:
                    path_d += f" L {point}"
                path_strings.append(path_d)
    
    # Extraire les <polygon>
    for polygon in doc.getElementsByTagName('polygon'):
        points = polygon.getAttribute('points')
        if points:
            points_list = points.strip().split()
            if points_list:
                path_d = f"M {points_list[0]}"
                for point in points_list[1:]:
                    path_d += f" L {point}"
                path_d += " Z"  # Fermer le polygone
                path_strings.append(path_d)
    
    # Extraire les <circle>
    for circle in doc.getElementsByTagName('circle'):
        cx = float(circle.getAttribute('cx') or 0)
        cy = float(circle.getAttribute('cy') or 0)
        r = float(circle.getAttribute('r') or 0)
        if r > 0:
            # Approximer le cercle avec un chemin
            path_d = f"M {cx+r},{cy} A {r},{r} 0 1,1 {cx-r},{cy} A {r},{r} 0 1,1 {cx+r},{cy} Z"
            path_strings.append(path_d)
    
    # Extraire les <ellipse>
    for ellipse in doc.getElementsByTagName('ellipse'):
        cx = float(ellipse.getAttribute('cx') or 0)
        cy = float(ellipse.getAttribute('cy') or 0)
        rx = float(ellipse.getAttribute('rx') or 0)
        ry = float(ellipse.getAttribute('ry') or 0)
        if rx > 0 and ry > 0:
            path_d = f"M {cx+rx},{cy} A {rx},{ry} 0 1,1 {cx-rx},{cy} A {rx},{ry} 0 1,1 {cx+rx},{cy} Z"
            path_strings.append(path_d)
    
    # Extraire les <rect>
    for rect in doc.getElementsByTagName('rect'):
        x = float(rect.getAttribute('x') or 0)
        y = float(rect.getAttribute('y') or 0)
        w = float(rect.getAttribute('width') or 0)
        h = float(rect.getAttribute('height') or 0)
        if w > 0 and h > 0:
            path_d = f"M {x},{y} L {x+w},{y} L {x+w},{y+h} L {x},{y+h} Z"
            path_strings.append(path_d)
    
    doc.unlink()
    
    if not path_strings:
        raise ValueError("Aucun chemin trouvé dans le SVG")
    
    print(f"  → {len(path_strings)} élément(s) trouvé(s)")
    return path_strings

def sample_svg_path(path_string, num_points=1000):
    """Échantillonne uniformément un chemin SVG en points complexes."""
    path = parse_path(path_string)
    
    if path.length() == 0:
        return np.array([])
    
    # Échantillonner uniformément le long du chemin
    points = []
    for i in range(num_points):
        t = i / num_points
        point = path.point(t)
        points.append(complex(point.real, point.imag))
    
    return np.array(points)

def calculate_connection_distance(path1, path2, reversed1=False, reversed2=False):
    """Calcule la distance de connexion entre deux chemins avec orientations."""
    end1 = path1[-1] if not reversed1 else path1[0]
    start2 = path2[0] if not reversed2 else path2[-1]
    return abs(end1 - start2)

def optimize_path_order_full(all_paths_points, max_iterations=200):
    """Optimise l'ordre ET l'orientation des chemins avec recherche exhaustive améliorée."""
    if len(all_paths_points) <= 1:
        return all_paths_points, [False] * len(all_paths_points)
    
    n = len(all_paths_points)
    print(f"  → Optimisation complète de {n} chemins...")
    
    # Si peu de chemins, essayer toutes les combinaisons
    if n <= 8:
        print("  → Recherche exhaustive (peu de chemins)...")
        from itertools import permutations
        
        best_order = None
        best_reversals = None
        best_distance = float('inf')
        
        # Tester toutes les permutations
        for perm in permutations(range(n)):
            # Pour chaque permutation, tester toutes les combinaisons d'orientations
            for rev_mask in range(2 ** n):
                reversals = [(rev_mask >> i) & 1 for i in range(n)]
                
                # Calculer la distance totale
                total_dist = 0
                for i in range(n - 1):
                    idx1, idx2 = perm[i], perm[i+1]
                    total_dist += calculate_connection_distance(
                        all_paths_points[idx1],
                        all_paths_points[idx2],
                        reversals[idx1],
                        reversals[idx2]
                    )
                
                if total_dist < best_distance:
                    best_distance = total_dist
                    best_order = list(perm)
                    best_reversals = [reversals[idx] for idx in perm]
        
        print(f"  → Distance optimale trouvée: {best_distance:.1f} pixels")
        return [all_paths_points[i] for i in best_order], best_reversals
    
    # Pour beaucoup de chemins, utiliser un algorithme glouton amélioré
    print("  → Algorithme glouton avec backtracking...")
    
    # Commencer par le chemin le plus central
    centers = [np.mean(path) for path in all_paths_points]
    global_center = np.mean(centers)
    start_idx = np.argmin([abs(c - global_center) for c in centers])
    
    # État: (ordre_des_indices, orientations_inversées, distance_totale)
    best_solution = None
    best_distance = float('inf')
    
    # Essayer plusieurs points de départ
    for start_idx in range(min(n, 5)):  # Tester les 5 premiers chemins
        current_order = [start_idx]
        current_reversals = [False]
        available = set(range(n)) - {start_idx}
        current_distance = 0
        
        # Glouton: toujours prendre le meilleur suivant
        while available:
            best_next_dist = float('inf')
            best_next_idx = -1
            best_next_reversed = False
            
            # Tester toutes les options restantes
            for idx in available:
                # Tester les 2 orientations
                for reversed_next in [False, True]:
                    dist = calculate_connection_distance(
                        all_paths_points[current_order[-1]],
                        all_paths_points[idx],
                        current_reversals[-1],
                        reversed_next
                    )
                    
                    if dist < best_next_dist:
                        best_next_dist = dist
                        best_next_idx = idx
                        best_next_reversed = reversed_next
            
            current_order.append(best_next_idx)
            current_reversals.append(best_next_reversed)
            current_distance += best_next_dist
            available.remove(best_next_idx)
        
        if current_distance < best_distance:
            best_distance = current_distance
            best_solution = (current_order, current_reversals)
            print(f"    → Point de départ {start_idx}: distance = {best_distance:.1f}")
    
    best_order, best_reversals = best_solution
    
    # Amélioration avec 2-opt sur l'ordre (en gardant les orientations optimales)
    print("  → Amélioration 2-opt...")
    improved = True
    iteration = 0
    
    while improved and iteration < max_iterations:
        improved = False
        iteration += 1
        
        for i in range(1, n - 1):
            for j in range(i + 1, n):
                # Essayer de renverser le segment [i:j]
                new_order = best_order[:i] + best_order[i:j+1][::-1] + best_order[j+1:]
                new_reversals = best_reversals[:i] + best_reversals[i:j+1][::-1] + best_reversals[j+1:]
                
                # Recalculer la distance
                new_distance = 0
                for k in range(n - 1):
                    idx1, idx2 = new_order[k], new_order[k+1]
                    new_distance += calculate_connection_distance(
                        all_paths_points[idx1],
                        all_paths_points[idx2],
                        new_reversals[k],
                        new_reversals[k+1]
                    )
                
                if new_distance < best_distance:
                    best_order = new_order
                    best_reversals = new_reversals
                    best_distance = new_distance
                    improved = True
                    print(f"    → Itération {iteration}: distance = {best_distance:.1f}")
                    break
            
            if improved:
                break
    
    print(f"  → Optimisation terminée: {best_distance:.1f} pixels totaux")
    
    # Réorganiser et inverser les chemins selon la solution optimale
    optimized_paths = []
    for idx, reversed_flag in zip(best_order, best_reversals):
        path = all_paths_points[idx]
        if reversed_flag:
            path = path[::-1]
        optimized_paths.append(path)
    
    return optimized_paths, best_reversals

def connect_paths_sequentially(ordered_paths):
    """Connecte les chemins déjà ordonnés de manière séquentielle."""
    if len(ordered_paths) == 0:
        return np.array([])
    
    if len(ordered_paths) == 1:
        return ordered_paths[0]
    
    print(f"  → Connexion séquentielle de {len(ordered_paths)} chemins...")
    
    result = list(ordered_paths[0])
    total_jump_distance = 0
    num_jumps = 0
    
    for i in range(1, len(ordered_paths)):
        current_end = result[-1]
        next_start = ordered_paths[i][0]
        
        jump_distance = abs(next_start - current_end)
        total_jump_distance += jump_distance
        
        # Créer une connexion lisse
        if jump_distance > 3:
            num_points = max(2, int(jump_distance / 2))
            connection = np.linspace(current_end, next_start, num_points)[1:-1]
            result.extend(connection)
            num_jumps += 1
            if jump_distance > 20:
                print(f"    → Saut #{num_jumps}: {jump_distance:.1f} pixels")
        
        result.extend(ordered_paths[i])
    
    print(f"  → {num_jumps} sauts totaux, distance moyenne: {total_jump_distance/max(1,num_jumps):.1f} pixels")
    
    return np.array(result)

def normalize_points(points):
    """Centre et normalise les points pour la visualisation."""
    if len(points) == 0:
        return points
    
    # Centrer
    points = points - np.mean(points)
    
    # Normaliser et mettre à l'échelle
    scale = np.max(np.abs(points))
    if scale > 0:
        points = points / scale
    points = points * 250
    
    # Inverser y pour avoir le bon sens visuel
    points = points.real - 1j * points.imag
    
    return points

def compute_fourier_coefficients(points, n_coeffs):
    """Calcule les coefficients de Fourier complexes."""
    N = len(points)
    coeffs = []
    
    # Calculer les coefficients pour les fréquences de -n_coeffs à +n_coeffs
    for n in range(-n_coeffs, n_coeffs + 1):
        coeff = np.sum(points * np.exp(-2j * np.pi * n * np.arange(N) / N)) / N
        coeffs.append((n, coeff))
    
    # Trier par magnitude décroissante
    coeffs.sort(key=lambda x: abs(x[1]), reverse=True)
    
    return coeffs[:min(2*n_coeffs+1, len(coeffs))]

def epicycle_point(coeffs, t):
    """Calcule la position finale et tous les vecteurs intermédiaires."""
    positions = [0j]  # Commence à l'origine
    
    for freq, coeff in coeffs:
        prev = positions[-1]
        rotation = coeff * np.exp(2j * np.pi * freq * t)
        positions.append(prev + rotation)
    
    return positions

# ====== EXTRACTION DU CHEMIN ======
if input_path:
    try:
        print(f"Chargement du SVG: {input_path}")
        path_strings = extract_paths_from_svg(input_path)
        
        print("Échantillonnage des chemins...")
        all_paths_points = []
        for i, path_string in enumerate(path_strings):
            try:
                points_per_path = max(100, sample_points // len(path_strings))
                path_points = sample_svg_path(path_string, points_per_path)
                if len(path_points) > 0:
                    all_paths_points.append(path_points)
                    print(f"  → Chemin {i+1}: {len(path_points)} points")
            except Exception as e:
                print(f"  → Chemin {i+1}: erreur ({e})")
        
        if not all_paths_points:
            raise ValueError("Aucun point extrait des chemins SVG")
        
        print("Connexion des chemins...")
        # Optimisation complète: ordre ET orientation
        ordered_paths, reversals = optimize_path_order_full(all_paths_points, max_iterations=100)
        # Connexion séquentielle des chemins déjà optimisés
        points = connect_paths_sequentially(ordered_paths)
        print(f"  → Chemin final: {len(points)} points")
        
        points = normalize_points(points)
        
    except Exception as e:
        print(f"Erreur lors du chargement du SVG: {e}")
        print("Utilisation d'une forme de test à la place.")
        points = generate_test_shape()
else:
    print("Génération d'une forme de test...")
    points = generate_test_shape()

print(f"Calcul de {n_coeffs} coefficients de Fourier...")
coeffs = compute_fourier_coefficients(points, n_coeffs)
print(f"  → {len(coeffs)} vecteurs calculés")

# ====== CONFIGURATION DE L'ANIMATION ======
total_frames = int(duration * fps)
fig, ax = plt.subplots(figsize=(10, 10))
ax.set_xlim(-350, 350)
ax.set_ylim(-350, 350)
ax.set_aspect('equal')
ax.grid(True, alpha=0.3)
ax.set_facecolor('#f0f0f0')

# Éléments graphiques
circles = []
vectors = []
for _ in coeffs:
    circle, = ax.plot([], [], 'c-', alpha=0.3, linewidth=1)
    vector, = ax.plot([], [], 'b-', linewidth=2)
    circles.append(circle)
    vectors.append(vector)

drawing_line, = ax.plot([], [], 'r-', linewidth=2)
path_history = []

def init():
    """Initialise l'animation."""
    for circle in circles:
        circle.set_data([], [])
    for vector in vectors:
        vector.set_data([], [])
    drawing_line.set_data([], [])
    return circles + vectors + [drawing_line]

def animate(frame):
    """Met à jour chaque frame de l'animation."""
    t = frame / total_frames
    
    # Calculer toutes les positions des vecteurs
    positions = epicycle_point(coeffs, t)
    
    # Dessiner les cercles et vecteurs
    for i, (freq, coeff) in enumerate(coeffs):
        center = positions[i]
        radius = abs(coeff)
        
        # Cercle
        theta = np.linspace(0, 2*np.pi, 50)
        circle_x = center.real + radius * np.cos(theta)
        circle_y = center.imag + radius * np.sin(theta)
        circles[i].set_data(circle_x, circle_y)
        
        # Vecteur
        end = positions[i+1]
        vectors[i].set_data([center.real, end.real], [center.imag, end.imag])
    
    # Ajouter le point final au tracé
    final_point = positions[-1]
    path_history.append(final_point)
    
    # Limiter l'historique
    if len(path_history) > total_frames:
        path_history.pop(0)
    
    # Dessiner le tracé
    if len(path_history) > 1:
        path_x = [p.real for p in path_history]
        path_y = [p.imag for p in path_history]
        drawing_line.set_data(path_x, path_y)
    
    # Réinitialiser l'historique à la fin du cycle
    if frame == total_frames - 1:
        path_history.clear()
    
    return circles + vectors + [drawing_line]

# ====== LANCER L'ANIMATION ======
print(f"Lancement de l'animation ({duration}s par cycle à {fps} FPS)...")
anim = FuncAnimation(
    fig, 
    animate, 
    init_func=init,
    frames=total_frames,
    interval=1000/fps,
    blit=True,
    repeat=True
)

plt.title(f"Série de Fourier - {len(coeffs)} vecteurs", fontsize=14, pad=20)
plt.tight_layout()
plt.show()