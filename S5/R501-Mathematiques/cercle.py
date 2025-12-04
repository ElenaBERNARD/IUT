import pygame
import math
import sys

# --- Paramètres principaux ---
WIDTH, HEIGHT = 1200, 1000
FPS = 60

# Mouvement du centre principal
centreX, centreY = WIDTH // 2, HEIGHT // 2
rayonX, rayonY = 500, 400
vitesseRadX, vitesseRadY = 2.5, 5.0
decalageRadX, decalageRadY = 0.0, 7.0

# Mouvement de l’objet secondaire autour du point principal
orbit_rayonX, orbit_rayonY = 100, 50
orbit_vitesseX, orbit_vitesseY = 10.0, 0.1
orbit_decalageX, orbit_decalageY = 0.0, 0.0

# Couleurs
RED = (255, 0, 0)
YELLOW = (255, 200, 0)
BLACK = (0, 0, 0)
PINK = (255, 105, 180)

# Initialisation
pygame.init()
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("Mouvement circulaire imbriqué")
clock = pygame.time.Clock()

trail_surface = pygame.Surface((WIDTH, HEIGHT), pygame.SRCALPHA)
trail_decay = 2  # plus petit = traînée plus persistante

t = 0
frame = 0
running = True
while running:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            running = False

    # --- Calcul du point principal ---
    x_centre = centreX + rayonX * math.cos(vitesseRadX * t + decalageRadX)
    y_centre = centreY + rayonY * math.sin(vitesseRadY * t + decalageRadY)

    # --- Calcul de l’objet secondaire autour du point principal ---
    x_obj = x_centre + orbit_rayonX * math.cos(orbit_vitesseX * t + orbit_decalageX)
    y_obj = y_centre + orbit_rayonY * math.sin(orbit_vitesseY * t + orbit_decalageY)

    # Effet de traînée
    if frame % 10 == 0:
        trail_surface.fill((0, 0, 0, trail_decay), special_flags=pygame.BLEND_RGBA_SUB)

    # Dessins
    pygame.draw.circle(trail_surface, RED, (int(x_centre), int(y_centre)), 5)      # centre principal
    pygame.draw.circle(trail_surface, YELLOW, (int(x_obj), int(y_obj)), 4)         # objet secondaire
    # if frame % 3 == 0:
    #     pygame.draw.line(trail_surface, PINK, (int(x_centre), int(y_centre)), (int(x_obj), int(y_obj)), 1)

    screen.fill(BLACK)
    screen.blit(trail_surface, (0, 0))

    pygame.display.flip()
    t += 0.01
    frame += 1
    clock.tick(FPS)

pygame.quit()
sys.exit()
