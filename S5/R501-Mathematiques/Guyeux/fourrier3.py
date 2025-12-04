import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation
from svgpathtools import svg2paths

# ====== PARAMÈTRES À MODIFIER ======
input_path = "dessin.svg"  # Fichier SVG à lire
n_coeffs = 100             # Nombre de vecteurs de Fourier
duration = 30              # Durée d’un cycle complet (secondes)
fps = 60                   # Images par seconde
# ===================================


# ====== EXTRACTION DU CHEMIN À PARTIR DU SVG ======
def load_svg_points(path, num_points=4000):
    """Charge un SVG et renvoie une liste de points complexes uniformément échantillonnés."""
    paths, _ = svg2paths(path)
    all_points = []

    for p in paths:
        for t in np.linspace(0, 1, num_points // len(paths)):
            z = p.point(t)
            all_points.append(complex(z.real, -z.imag))  # inverser Y pour correspondre à matplotlib

    points = np.array(all_points)

    # Centrer
    points -= np.mean(points)
    # Normaliser
    points /= np.max(np.abs(points))
    # Échelle visuelle
    points *= 250

    return points


def compute_fourier_coefficients(points, n_coeffs):
    """Calcule les coefficients complexes de Fourier pour les points donnés."""
    N = len(points)
    coeffs = []

    for n in range(-n_coeffs, n_coeffs + 1):
        coeff = np.sum(points * np.exp(-2j * np.pi * n * np.arange(N) / N)) / N
        coeffs.append((n, coeff))

    # Trier par amplitude décroissante (les plus forts d’abord)
    coeffs.sort(key=lambda x: abs(x[1]), reverse=True)
    return coeffs


def epicycle_positions(coeffs, t):
    """Retourne la liste des positions complexes pour chaque vecteur à l’instant t."""
    pos = [0j]
    for freq, coeff in coeffs:
        prev = pos[-1]
        pos.append(prev + coeff * np.exp(2j * np.pi * freq * t))
    return pos


# ====== CHARGEMENT ET CALCUL ======
print("Chargement du SVG et extraction des points...")
points = load_svg_points(input_path)
print(f"{len(points)} points extraits du SVG")

print("Calcul des coefficients de Fourier...")
coeffs = compute_fourier_coefficients(points, n_coeffs)
print(f"{len(coeffs)} vecteurs calculés")


# ====== ANIMATION ======
total_frames = int(duration * fps)
fig, ax = plt.subplots(figsize=(8, 8))
ax.set_xlim(-350, 350)
ax.set_ylim(-350, 350)
ax.set_aspect('equal')
ax.set_facecolor('#f8f8f8')
ax.set_title(f"Série de Fourier ({n_coeffs} vecteurs)")

circles = []
vectors = []
for _ in coeffs:
    c, = ax.plot([], [], 'c-', alpha=0.3, linewidth=1)
    v, = ax.plot([], [], 'b-', linewidth=2)
    circles.append(c)
    vectors.append(v)

trace, = ax.plot([], [], 'r-', linewidth=2)
path_points = []

def init():
    for c in circles: c.set_data([], [])
    for v in vectors: v.set_data([], [])
    trace.set_data([], [])
    path_points.clear()
    return circles + vectors + [trace]

def animate(frame):
    t = frame / total_frames
    positions = epicycle_positions(coeffs, t)

    # Dessine cercles et vecteurs
    for i, (freq, coeff) in enumerate(coeffs):
        center = positions[i]
        end = positions[i + 1]
        radius = abs(coeff)

        theta = np.linspace(0, 2*np.pi, 50)
        cx = center.real + radius * np.cos(theta)
        cy = center.imag + radius * np.sin(theta)
        circles[i].set_data(cx, cy)
        vectors[i].set_data([center.real, end.real], [center.imag, end.imag])

    # Tracé rouge final
    final = positions[-1]
    path_points.append(final)
    if len(path_points) > total_frames:
        path_points.pop(0)
    trace.set_data([p.real for p in path_points], [p.imag for p in path_points])

    # Boucle infinie
    if frame == total_frames - 1:
        path_points.clear()

    return circles + vectors + [trace]

print(f"Lancement de l’animation ({duration}s, {fps} FPS)...")
anim = FuncAnimation(fig, animate, init_func=init, frames=total_frames, interval=1000/fps, blit=True, repeat=True)
plt.tight_layout()
plt.show()
