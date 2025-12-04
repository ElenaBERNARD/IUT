import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from PIL import Image, ImageFilter, ImageDraw
from collections import deque

# Tentative d'import OpenCV (optionnel)
try:
    import cv2
    have_cv2 = True
except ImportError:
    have_cv2 = False
    print("OpenCV non disponible, utilisation du fallback pour l'extraction des contours")

# ====== PARAMÈTRES À MODIFIER ======
input_path = "cat_dog.jpg"  # Chemin de l'image (vide = figure de test)
n_coeffs = 100   # Nombre de vecteurs de Fourier
duration = 10    # Durée d'un cycle complet (secondes)
fps = 60         # Images par seconde
invert = True    # True si dessin noir sur fond blanc
blur = 1         # Flou gaussien avant binarisation
# ===================================

def load_and_binarize(path, invert=True, blur=1):
    """Charge et binarise une image."""
    try:
        im = Image.open(path).convert("L")
    except Exception as e:
        raise
    if blur > 0:
        im = im.filter(ImageFilter.GaussianBlur(blur))
    # Seuillage automatique
    arr = np.array(im)
    thresh = arr.mean() * 0.9
    bw = arr < thresh if invert else arr > thresh
    return Image.fromarray((bw * 255).astype(np.uint8))

def make_demo_image(size=(512, 512)):
    """Crée une image de démonstration."""
    im = Image.new("L", size, 255)
    draw = ImageDraw.Draw(im)
    # Dessiner un tracé de démonstration
    pts = [(50,400),(150,300),(200,340),(260,220),(320,260),(380,150),(430,200),(470,120)]
    draw.line(pts, fill=0, width=6)
    draw.ellipse((240,320,280,360), outline=0, width=6)
    return im

def find_contours_from_pil(bw_im):
    """Trouve les contours dans une image binarisée."""
    arr = np.array(bw_im)
    if have_cv2:
        # Utiliser OpenCV pour trouver les contours avec CHAIN_APPROX_NONE pour tous les points
        cnts = cv2.findContours(arr, cv2.RETR_LIST, cv2.CHAIN_APPROX_NONE)
        cnts = cnts[0] if len(cnts) == 2 else cnts[1]
        contours = []
        for c in cnts:
            c = c.squeeze()
            if c.ndim == 1:
                if c.shape[0] == 2:  # Un seul point
                    contours.append([(int(c[0]), int(c[1]))])
                continue
            if len(c) > 2:  # Ignorer les contours trop petits
                contours.append([(int(p[0]), int(p[1])) for p in c])
        return contours
    else:
        # Fallback amélioré avec skimage
        from skimage import measure
        # Trouver tous les contours
        contours_sk = measure.find_contours(arr, 0.5)
        contours = []
        for contour in contours_sk:
            if len(contour) > 2:
                # Convertir de (row, col) à (x, y)
                pts = [(int(pt[1]), int(pt[0])) for pt in contour]
                contours.append(pts)
        return contours

def skeletonize_image(bw_im):
    """Squelettise l'image pour obtenir des lignes fines."""
    arr = np.array(bw_im)
    if have_cv2:
        # Squelettisation avec OpenCV
        kernel = cv2.getStructuringElement(cv2.MORPH_CROSS, (3, 3))
        skeleton = np.zeros_like(arr)
        while True:
            eroded = cv2.erode(arr, kernel)
            temp = cv2.dilate(eroded, kernel)
            temp = cv2.subtract(arr, temp)
            skeleton = cv2.bitwise_or(skeleton, temp)
            arr = eroded.copy()
            if cv2.countNonZero(arr) == 0:
                break
        return Image.fromarray(skeleton)
    else:
        # Utiliser skimage morphology
        from skimage.morphology import skeletonize
        skeleton = skeletonize(arr > 0)
        return Image.fromarray((skeleton * 255).astype(np.uint8))

def interpolate_path(path, num_points=None):
    """Interpole le chemin pour avoir un nombre uniforme de points."""
    if len(path) < 2:
        return path
    
    # Calculer les distances cumulées
    path_arr = np.array(path)
    diffs = np.diff(path_arr, axis=0)
    distances = np.sqrt((diffs**2).sum(axis=1))
    cumulative = np.concatenate([[0], np.cumsum(distances)])
    total_length = cumulative[-1]
    
    if num_points is None:
        # Utiliser environ un point tous les 2-3 pixels
        num_points = max(100, int(total_length / 2.5))
    
    # Interpoler uniformément
    new_distances = np.linspace(0, total_length, num_points)
    new_x = np.interp(new_distances, cumulative, path_arr[:, 0])
    new_y = np.interp(new_distances, cumulative, path_arr[:, 1])
    
    return [(int(x), int(y)) for x, y in zip(new_x, new_y)]

def connect_contours_optimal(contours):
    """Connecte les contours de manière optimale avec interpolation."""
    if not contours:
        return []
    
    # Filtrer les contours trop petits
    contours = [c for c in contours if len(c) > 2]
    
    if not contours:
        return []
    
    print(f"  → Connexion de {len(contours)} contours...")
    
    # Trier par longueur décroissante
    contours.sort(key=len, reverse=True)
    
    # Commencer avec le plus long contour
    path = list(contours[0])
    used = {0}
    
    # Connecter les autres contours
    while len(used) < len(contours):
        end_point = path[-1]
        
        best_dist = float('inf')
        best_idx = -1
        best_reversed = False
        best_is_start = False
        
        for idx, contour in enumerate(contours):
            if idx in used:
                continue
            
            # Tester 4 configurations: début→début, début→fin, fin→début, fin→fin
            start_contour = contour[0]
            end_contour = contour[-1]
            
            # Distance à la fin de notre chemin
            dist_to_start = np.hypot(start_contour[0] - end_point[0], start_contour[1] - end_point[1])
            dist_to_end = np.hypot(end_contour[0] - end_point[0], end_contour[1] - end_point[1])
            
            if dist_to_start < best_dist:
                best_dist = dist_to_start
                best_idx = idx
                best_reversed = False
                best_is_start = False
            
            if dist_to_end < best_dist:
                best_dist = dist_to_end
                best_idx = idx
                best_reversed = True
                best_is_start = False
            
            # Si le chemin actuel est petit, on peut aussi connecter au début
            if len(path) < 100:
                start_path = path[0]
                dist_start_to_start = np.hypot(start_contour[0] - start_path[0], start_contour[1] - start_path[1])
                dist_start_to_end = np.hypot(end_contour[0] - start_path[0], end_contour[1] - start_path[1])
                
                if dist_start_to_start < best_dist:
                    best_dist = dist_start_to_start
                    best_idx = idx
                    best_reversed = True
                    best_is_start = True
                
                if dist_start_to_end < best_dist:
                    best_dist = dist_start_to_end
                    best_idx = idx
                    best_reversed = False
                    best_is_start = True
        
        if best_idx == -1:
            break
        
        # Ajouter le contour
        next_contour = list(contours[best_idx])
        if best_reversed:
            next_contour = next_contour[::-1]
        
        # Interpoler la ligne de connexion si la distance est grande
        if best_dist > 5:
            if best_is_start:
                connection = interpolate_connection(next_contour[-1], path[0], int(best_dist))
                path = next_contour + connection + path
            else:
                connection = interpolate_connection(path[-1], next_contour[0], int(best_dist))
                path.extend(connection)
                path.extend(next_contour)
        else:
            if best_is_start:
                path = next_contour + path
            else:
                path.extend(next_contour)
        
        used.add(best_idx)
    
    return path

def interpolate_connection(pt1, pt2, num_points):
    """Crée une ligne interpolée entre deux points."""
    if num_points < 2:
        return []
    x_vals = np.linspace(pt1[0], pt2[0], num_points)
    y_vals = np.linspace(pt1[1], pt2[1], num_points)
    return [(int(x), int(y)) for x, y in zip(x_vals, y_vals)][1:-1]

def path_to_complex(path):
    """Convertit un chemin de points (x, y) en nombres complexes."""
    if not path:
        return np.array([])
    
    points = np.array([x + 1j * y for x, y in path])
    
    # Centrer
    points -= np.mean(points)
    
    # Normaliser et mettre à l'échelle
    scale = np.max(np.abs(points))
    if scale > 0:
        points /= scale
    points *= 250  # Échelle pour la visualisation
    
    # Inverser y pour avoir le bon sens
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
    
    return coeffs[:2*n_coeffs+1]

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
        print("Chargement de l'image...")
        bw_im = load_and_binarize(input_path, invert=invert, blur=blur)
        
        print("Squelettisation de l'image...")
        skeleton_im = skeletonize_image(bw_im)
        
        print("Extraction des contours...")
        contours = find_contours_from_pil(skeleton_im)
        print(f"  → {len(contours)} contour(s) trouvé(s)")
        
        if not contours:
            raise ValueError("Aucun contour trouvé dans l'image")
        
        print("Connexion optimale des contours...")
        path = connect_contours_optimal(contours)
        print(f"  → Chemin brut de {len(path)} points")
        
        print("Interpolation du chemin...")
        path = interpolate_path(path, num_points=2000)
        print(f"  → Chemin interpolé de {len(path)} points")
        
        points = path_to_complex(path)
        
    except Exception as e:
        print(f"Erreur lors du chargement de l'image: {e}")
        print("Utilisation d'une image de démonstration à la place.")
        bw_im = make_demo_image()
        skeleton_im = skeletonize_image(bw_im)
        contours = find_contours_from_pil(skeleton_im)
        path = connect_contours_optimal(contours)
        path = interpolate_path(path, num_points=2000)
        points = path_to_complex(path)
else:
    print("Génération d'une image de démonstration...")
    bw_im = make_demo_image()
    skeleton_im = skeletonize_image(bw_im)
    contours = find_contours_from_pil(skeleton_im)
    path = connect_contours_optimal(contours)
    path = interpolate_path(path, num_points=2000)
    points = path_to_complex(path)

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
    
    # Limiter l'historique pour éviter la surcharge mémoire
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