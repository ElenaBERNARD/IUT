import pygame
import numpy as np
import math
from xml.dom import minidom
from svg.path import parse_path
import sys

# ====== PARAMÈTRES ======
INPUT_PATH = "hamburger.svg"  # Mettre "" pour la forme de test (cœur)
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
        points = x + 1j * y
        # Centrage et normalisation immédiate pour le cœur
        points = points - np.mean(points)
        return [points * 10] # Retourne une liste contenant un seul chemin

    @staticmethod
    def load_svg_paths(filepath):
        """Extrait les chemins d'un SVG sous forme de liste de tableaux numpy."""
        try:
            doc = minidom.parse(filepath)
            path_strings = [path.getAttribute('d') for path in doc.getElementsByTagName('path')]
            doc.unlink()
            
            if not path_strings:
                print("Aucun path trouvé, utilisation du cœur.")
                return SVGHandler.generate_heart()

            raw_paths = []
            
            for d in path_strings:
                path = parse_path(d)
                length = path.length()
                if length == 0: continue
                
                # Échantillonnage adaptatif par chemin
                n_pts = max(10, int(length)) # Au moins 10 points, ou 1 pt/pixel
                pts_list = []
                for i in range(n_pts):
                    pt = path.point(i / n_pts)
                    pts_list.append(complex(pt.real, pt.imag))
                
                if pts_list:
                    raw_paths.append(np.array(pts_list))
            
            return raw_paths

        except Exception as e:
            print(f"Erreur SVG ({e}), utilisation du cœur.")
            return SVGHandler.generate_heart()

class PathOptimizer:
    @staticmethod
    def normalize_paths(paths_list, target_radius=300):
        """Centre et normalise l'ensemble des chemins."""
        if not paths_list: return []
        
        # 1. Tout concaténer pour trouver le centre global et l'échelle
        all_pts = np.concatenate(paths_list)
        center = np.mean(all_pts)
        max_dim = np.max(np.abs(all_pts - center))
        
        normalized_paths = []
        if max_dim == 0: max_dim = 1
        
        scale = target_radius / max_dim
        
        for path in paths_list:
            normalized_paths.append((path - center) * scale)
            
        return normalized_paths

    @staticmethod
    def flatten(paths_list):
        """Fusionne une liste de chemins en un seul tableau de points."""
        if not paths_list: return np.array([])
        return np.concatenate(paths_list)

    @staticmethod
    def optimize_tsp(paths_list):
        """
        Réorganise les chemins pour minimiser les sauts (Algorithme Glouton / Nearest Neighbor).
        Peut inverser les chemins si nécessaire.
        """
        if not paths_list: return []
        
        # On travaille sur une copie pour ne pas casser l'ordre original
        pool = list(paths_list)
        ordered = [pool.pop(0)] # On commence par le premier (ou le plus grand ?)
        
        while pool:
            current_end = ordered[-1][-1]
            best_dist = float('inf')
            best_idx = -1
            should_reverse = False
            
            for i, path in enumerate(pool):
                # Distance vers le début du candidat
                start = path[0]
                dist_start = abs(start - current_end)
                
                # Distance vers la fin du candidat (si on le renverse)
                end = path[-1]
                dist_end = abs(end - current_end)
                
                if dist_start < best_dist:
                    best_dist = dist_start
                    best_idx = i
                    should_reverse = False
                
                if dist_end < best_dist:
                    best_dist = dist_end
                    best_idx = i
                    should_reverse = True
            
            # Ajouter le meilleur candidat
            next_path = pool.pop(best_idx)
            if should_reverse:
                next_path = next_path[::-1] # Inverser le tableau numpy
            
            ordered.append(next_path)
            
        return ordered

class FourierEpicycles:
    def __init__(self, n_coeffs):
        self.n_coeffs = n_coeffs
        self.coeffs = []
        self.time = 0
        self.path = []
        self.prev_point = None
        self.points_source = None # Pour savoir si on doit recalculer

    def set_points(self, points):
        """Recalcule la DFT pour un nouvel ensemble de points."""
        self.points_source = points
        self.coeffs = self.compute_dft(points, self.n_coeffs)
        self.time = 0
        self.path = []
        self.prev_point = None

    def compute_dft(self, points, n_coeffs):
        N = len(points)
        if N == 0: return []
        
        coeffs = []
        freqs = [0]
        for i in range(1, n_coeffs + 1):
            freqs.append(i)
            freqs.append(-i)
            
        # Optimisation vectorisée
        n_indices = np.arange(N)
        for k in freqs:
            c = np.sum(points * np.exp(-2j * np.pi * k * n_indices / N)) / N
            coeffs.append({"freq": k, "amp": abs(c), "phase": np.angle(c)})
        
        coeffs.sort(key=lambda x: x["amp"], reverse=True)
        return coeffs

    def update_and_draw(self, surface, center_pos):
        if not self.coeffs: return

        x, y = center_pos
        current_pos = complex(x, y)
        
        # 1. Dessiner les cercles
        for item in self.coeffs:
            prev_pos = current_pos
            freq = item["freq"]
            radius = item["amp"] * SCALE_FACTOR
            phase = item["phase"]
            
            angle = freq * (2 * np.pi * self.time) + phase
            dx = radius * np.cos(angle)
            dy = radius * np.sin(angle)
            
            current_pos += complex(dx, dy)
            
            if radius > 1:
                pygame.draw.circle(surface, CIRCLE_COLOR, (int(prev_pos.real), int(prev_pos.imag)), int(radius), 1)
            if radius > 2:
                pygame.draw.line(surface, VECTOR_COLOR, (int(prev_pos.real), int(prev_pos.imag)), (int(current_pos.real), int(current_pos.imag)), 1)

        # 2. Gestion du tracé avec seuil
        if self.prev_point is not None:
            dist = abs(current_pos - self.prev_point)
            if dist < PEN_LIFT_THRESHOLD:
                self.path.append(current_pos)
            else:
                self.path.append(None) # Lever le crayon
        
        self.prev_point = current_pos
        
        # Dessin du path
        if len(self.path) > 1:
            points_to_draw = []
            for pt in self.path:
                if pt is None:
                    if len(points_to_draw) > 1:
                        pygame.draw.lines(surface, TRACE_COLOR, False, points_to_draw, 2)
                    points_to_draw = []
                else:
                    points_to_draw.append((int(pt.real), int(pt.imag)))
            if len(points_to_draw) > 1:
                pygame.draw.lines(surface, TRACE_COLOR, False, points_to_draw, 2)

        pygame.draw.circle(surface, (255, 0, 0), (int(current_pos.real), int(current_pos.imag)), 3)

        self.time += TIME_MULT
        if self.time > 1.0:
            self.time = 0
            self.path = []
            self.prev_point = None

def main():
    pygame.init()
    screen = pygame.display.set_mode(WINDOW_SIZE)
    pygame.display.set_caption("Optimisation Fourier - PyGame")
    clock = pygame.time.Clock()
    font = pygame.font.SysFont("monospace", 16)

    # 1. Chargement et Préparation
    print("Extraction des chemins SVG...")
    raw_paths_list = SVGHandler.load_svg_paths(INPUT_PATH)
    
    print("Normalisation...")
    # On normalise les morceaux individuels par rapport au centre global
    normalized_paths = PathOptimizer.normalize_paths(raw_paths_list)
    
    # 2. Création des deux jeux de données
    print("Génération du mode 'Strict' (Ordre SVG)...")
    points_strict = PathOptimizer.flatten(normalized_paths)
    
    print("Génération du mode 'Continu' (Optimisation TSP)...")
    optimized_paths = PathOptimizer.optimize_tsp(normalized_paths)
    points_continuous = PathOptimizer.flatten(optimized_paths)

    # 3. Initialisation Fourier
    fourier = FourierEpicycles(N_COEFFS)
    
    # État par défaut
    mode = "strict" # ou "continuous"
    fourier.set_points(points_strict)
    print("Prêt. Appuyez sur ESPACE pour changer de mode.")

    running = True
    while running:
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                running = False
            elif event.type == pygame.KEYDOWN:
                if event.key == pygame.K_ESCAPE:
                    running = False
                elif event.key == pygame.K_SPACE:
                    # Basculer de mode
                    if mode == "strict":
                        mode = "continuous"
                        fourier.set_points(points_continuous)
                        print("Mode: Continu (Sauts minimisés)")
                    else:
                        mode = "strict"
                        fourier.set_points(points_strict)
                        print("Mode: Strict (Respect du dessin)")

        screen.fill(BG_COLOR)
        center = (WINDOW_SIZE[0] // 2, WINDOW_SIZE[1] // 2)
        
        fourier.update_and_draw(screen, center)
        
        # Affichage UI
        fps_text = f"FPS: {int(clock.get_fps())}"
        mode_text = f"MODE: {'CONTINU (Optimisé)' if mode == 'continuous' else 'STRICT (Original)'}"
        
        screen.blit(font.render(fps_text, 1, (200, 200, 200)), (10, 10))
        screen.blit(font.render(mode_text, 1, TRACE_COLOR), (10, 30))
        screen.blit(font.render("[ESPACE] pour changer", 1, (150, 150, 150)), (10, 50))

        pygame.display.flip()
        clock.tick(60)

    pygame.quit()
    sys.exit()

if __name__ == "__main__":
    main()