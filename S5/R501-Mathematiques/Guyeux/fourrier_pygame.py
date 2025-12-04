import pygame
import numpy as np
import math
from xml.dom import minidom
from svg.path import parse_path
import sys

# ====== PARAMÈTRES ======
INPUT_PATH = "cat.svg"  # Mettre "" pour la forme de test (cœur)
WINDOW_SIZE = (1000, 800)
BG_COLOR = (20, 20, 25)       # Gris foncé élégant
TRACE_COLOR = (255, 255, 0)   # Jaune
VECTOR_COLOR = (100, 100, 100) # Gris clair pour les vecteurs
CIRCLE_COLOR = (50, 50, 60)    # Gris sombre pour les cercles
N_COEFFS = 100                # Nombre de cercles (complexité)
TIME_MULT = 0.002             # Vitesse de l'animation
SCALE_FACTOR = 1.0            # Zoom global
PEN_LIFT_THRESHOLD = 20       # Si le point bouge de + de X pixels en 1 frame, on ne dessine pas

class SVGHandler:
    @staticmethod
    def generate_heart():
        """Génère une forme de cœur mathématique pour tester."""
        t = np.linspace(0, 2*np.pi, 1000)
        x = 16 * np.sin(t)**3
        y = -(13 * np.cos(t) - 5 * np.cos(2*t) - 2 * np.cos(3*t) - np.cos(4*t))
        return x + 1j * y

    @staticmethod
    def load_svg(filepath, sample_points=2000):
        """Extrait et échantillonne les points d'un SVG."""
        try:
            doc = minidom.parse(filepath)
            path_strings = [path.getAttribute('d') for path in doc.getElementsByTagName('path')]
            doc.unlink()
            
            if not path_strings:
                print("Aucun path trouvé, utilisation du cœur.")
                return SVGHandler.generate_heart()

            all_points = []
            # On combine tous les chemins en une seule liste de points complexes
            for d in path_strings:
                path = parse_path(d)
                # On échantillonne proportionnellement à la longueur
                length = path.length()
                if length == 0: continue
                
                # Nombre de points basé sur la longueur (min 50)
                n_pts = int(length) + 50
                for i in range(n_pts):
                    pt = path.point(i / n_pts)
                    all_points.append(complex(pt.real, pt.imag))
            
            # Re-centrage et normalisation
            pts = np.array(all_points)
            pts = pts - np.mean(pts) # Centrer sur (0,0)
            
            # Mise à l'échelle pour s'adapter à l'écran
            max_dim = np.max(np.abs(pts))
            if max_dim > 0:
                pts = pts / max_dim * 300 # Rayon de base 300px
                
            return pts

        except Exception as e:
            print(f"Erreur SVG ({e}), utilisation du cœur.")
            return SVGHandler.generate_heart()

class FourierEpicycles:
    def __init__(self, points, n_coeffs):
        self.points = points
        self.coeffs = self.compute_dft(points, n_coeffs)
        self.time = 0
        self.path = []
        self.prev_point = None

    def compute_dft(self, points, n_coeffs):
        """Transformée de Fourier Discrète optimisée."""
        N = len(points)
        coeffs = []
        # On ne prend que les fréquences les plus importantes centrées autour de 0
        # 0, 1, -1, 2, -2, etc.
        freqs = [0]
        for i in range(1, n_coeffs + 1):
            freqs.append(i)
            freqs.append(-i)
            
        for k in freqs:
            # Formule DFT: sum(x[n] * e^(-2*pi*i*k*n/N))
            # Vectorisé avec numpy pour la vitesse
            n_indices = np.arange(N)
            c = np.sum(points * np.exp(-2j * np.pi * k * n_indices / N)) / N
            coeffs.append({"freq": k, "amp": abs(c), "phase": np.angle(c), "c": c})
        
        # On trie pour dessiner les plus grands cercles d'abord (meilleur visuellement)
        # Sauf le terme constant (centre) qui doit être géré en premier implicitement
        coeffs.sort(key=lambda x: x["amp"], reverse=True)
        return coeffs

    def update_and_draw(self, surface, center_pos):
        x, y = center_pos
        
        # Le premier point commence au centre de l'écran + le décalage du terme constant (freq 0)
        # Mais ici on a trié par amplitude, donc on fait la somme cumulative
        
        current_pos = complex(x, y)
        
        # 1. Dessiner les cercles et vecteurs
        for item in self.coeffs:
            prev_pos = current_pos
            freq = item["freq"]
            radius = item["amp"] * SCALE_FACTOR
            phase = item["phase"]
            
            # Calcul position: c * e^(2*pi*i*k*t)
            angle = freq * (2 * np.pi * self.time) + phase
            
            # Conversion polaire vers cartésien
            dx = radius * np.cos(angle)
            dy = radius * np.sin(angle)
            
            current_pos += complex(dx, dy)
            
            # Optimisation: Ne pas dessiner les cercles trop petits
            if radius > 1:
                pygame.draw.circle(surface, CIRCLE_COLOR, (int(prev_pos.real), int(prev_pos.imag)), int(radius), 1)
            
            # Vecteur (ligne)
            if radius > 2:
                pygame.draw.line(surface, VECTOR_COLOR, 
                                (int(prev_pos.real), int(prev_pos.imag)), 
                                (int(current_pos.real), int(current_pos.imag)), 1)

        # 2. Gestion du tracé (Trail)
        # Logique "Lever de crayon": 
        # Si c'est la première frame ou si la distance est trop grande (saut), on coupe.
        if self.prev_point is not None:
            dist = abs(current_pos - self.prev_point)
            
            # Si le saut est petit, on ajoute au tracé
            if dist < PEN_LIFT_THRESHOLD:
                self.path.append(current_pos)
            else:
                # C'est un grand saut (transition rapide de Fourier ou fin de cycle)
                # On insère un None pour dire "ne pas relier ces points"
                self.path.append(None)
        
        # Mettre à jour le point précédent
        self.prev_point = current_pos
        
        # Dessiner tout le chemin historique
        if len(self.path) > 1:
            # On dessine segment par segment pour gérer les coupures (None)
            # Pour la performance, on pourrait optimiser, mais PyGame gère bien quelques milliers de points.
            points_to_draw = []
            for pt in self.path:
                if pt is None:
                    if len(points_to_draw) > 1:
                        pygame.draw.lines(surface, TRACE_COLOR, False, points_to_draw, 2)
                    points_to_draw = []
                else:
                    points_to_draw.append((int(pt.real), int(pt.imag)))
            
            # Dessiner le dernier segment restant
            if len(points_to_draw) > 1:
                pygame.draw.lines(surface, TRACE_COLOR, False, points_to_draw, 2)

        # Dessiner la pointe du "crayon"
        pygame.draw.circle(surface, (255, 0, 0), (int(current_pos.real), int(current_pos.imag)), 3)

        # Avancer le temps
        dt = TIME_MULT
        self.time += dt
        
        # Reset propre à la fin du cycle
        if self.time > 1.0:
            self.time = 0
            self.path = [] # On efface le dessin pour recommencer
            self.prev_point = None # Important: Force le "lever de crayon" au début du prochain cycle

def main():
    pygame.init()
    screen = pygame.display.set_mode(WINDOW_SIZE)
    pygame.display.set_caption("Optimisation Fourier - PyGame")
    clock = pygame.time.Clock()

    # 1. Chargement et Calculs
    print("Génération des points...")
    points = SVGHandler.load_svg(INPUT_PATH)
    
    print(f"Calcul des coefficients ({N_COEFFS})...")
    fourier = FourierEpicycles(points, N_COEFFS)
    print("Prêt.")

    running = True
    while running:
        # Gestion des événements
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    running = False

        # Dessin
        screen.fill(BG_COLOR)
        
        # Centre de l'écran
        center = (WINDOW_SIZE[0] // 2, WINDOW_SIZE[1] // 2)
        
        # Mise à jour mathématique et affichage
        fourier.update_and_draw(screen, center)
        
        # UI Info
        font = pygame.font.SysFont("monospace", 15)
        label = font.render(f"FPS: {int(clock.get_fps())} | Vectors: {N_COEFFS}", 1, (255, 255, 255))
        screen.blit(label, (10, 10))

        pygame.display.flip()
        clock.tick(60) # Viser 60 FPS

    pygame.quit()
    sys.exit()

if __name__ == "__main__":
    main()