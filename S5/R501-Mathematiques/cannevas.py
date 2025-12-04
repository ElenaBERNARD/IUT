##Modules
import sys
import pygame
from numpy import *
import random

##Initialisation des couleurs
RED = (255, 0, 0)
GREEN = (0, 255, 0)
BLUE = (0, 0, 255)
YELLOW = (255, 255, 0)
WHITE = (255, 255, 255)
BLACK = (0, 0, 0)
PURPLE = (128, 0, 128)
ORANGE = (255, 165 ,0)
GREY = (128, 128, 128)
TURQUOISE = (64, 224, 208)

#-----------------Conditions initiales---------------
pygame.init()
screen = pygame.display.set_mode((1600,1000))
trail_surface = pygame.Surface((1600, 1000), pygame.SRCALPHA)
clock = pygame.time.Clock()

# Paramètres physiques
accel_mag = 0.7
drag_coeff = 0.98
max_speed = 30.0
G = 12000
restitution = 0.9
g = 0.0

# Planètes
planetes = [
    {"x": 600, "y": 400, "mass": 2000, "radius": 50, "color": YELLOW, "timer": random.randint(180, 300)},
    {"x": 1000, "y": 600, "mass": 1200, "radius": 30, "color": BLUE, "timer": random.randint(180, 300)},
    {"x": 400, "y": 800, "mass": 1500, "radius": 40, "color": GREEN, "timer": random.randint(180, 300)},
    {"x": 1200, "y": 300, "mass": 2500, "radius": 60, "color": PURPLE, "timer": random.randint(180, 300)}
]

# Création de 50 "joueurs"
n = 50
joueurs = []
for i in range(n):
    angle = random.uniform(0, 2*pi)
    joueurs.append({
        "x": random.uniform(100, 1500),
        "y": random.uniform(100, 900),
        "vx": 10 * cos(angle),
        "vy": 10 * sin(angle),
        "color": (random.randint(100,255), random.randint(100,255), random.randint(100,255))
    })

start = True

##Boucle principale
while True:
    clock.tick(60)
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

    screen.fill(BLACK)

    # Jeu
    if start:
        # Effet de traînée
        trail_surface.fill((0, 0, 0, 25), special_flags=pygame.BLEND_RGBA_SUB)

        for j in joueurs:
            ax = 0
            ay = 0

            # Gravité des planètes
            for p in planetes:
                px, py = p["x"], p["y"]
                mass, radius, color = p["mass"], p["radius"], p["color"]

                dx = px - j["x"]
                dy = py - j["y"]
                dist = sqrt(dx**2 + dy**2)
                if dist > 5:
                    force = G * mass / (dist**2)
                    if dist < radius * 2:
                        force *= (dist / (radius * 2))
                    ax += (dx / dist) * force / 10000
                    ay += (dy / dist) * force / 10000

            # Actualiser vitesse et position
            j["vx"] += ax
            j["vy"] += ay + g
            j["vx"] *= drag_coeff
            j["vy"] *= drag_coeff

            speed = sqrt(j["vx"]**2 + j["vy"]**2)
            if speed > max_speed:
                j["vx"] = (j["vx"] / speed) * max_speed
                j["vy"] = (j["vy"] / speed) * max_speed

            j["x"] += j["vx"]
            j["y"] += j["vy"]

            # Collisions avec planètes
            for p in planetes:
                px, py = p["x"], p["y"]
                mass, radius, color = p["mass"], p["radius"], p["color"]
                dx, dy = j["x"] - px, j["y"] - py
                dist = sqrt(dx**2 + dy**2)
                if dist < radius + 5:
                    nx, ny = dx / dist, dy / dist
                    j["x"] = px + nx * (radius + 5)
                    j["y"] = py + ny * (radius + 5)
                    vn = j["vx"] * nx + j["vy"] * ny
                    if vn < 0:
                        j["vx"] -= (1 + restitution) * vn * nx
                        j["vy"] -= (1 + restitution) * vn * ny
                        
            # Gestion des explosions planétaires
            for p in planetes:
                # Détection de boules proches et lentes
                nearby = [j for j in joueurs if sqrt((j["x"] - p["x"])**2 + (j["y"] - p["y"])**2) < p["radius"] * 2]
                slow = [j for j in nearby if sqrt(j["vx"]**2 + j["vy"]**2) < 1.5]

                if len(nearby) > 5 and len(slow) / max(len(nearby), 1) > 0.6:
                    p["timer"] -= 1  # accélère le compte à rebours si tout est calme
                else:
                    p["timer"] -= 0.3  # compte lent sinon

                # Explosion !
                if p["timer"] <= 0:
                    for j in nearby:
                        dx, dy = j["x"] - p["x"], j["y"] - p["y"]
                        dist = sqrt(dx**2 + dy**2)
                        if dist > 0:
                            # Force pulsée vers l’extérieur
                            intensity = 10 * (1 - dist / (p["radius"] * 2))
                            j["vx"] += (dx / dist) * intensity
                            j["vy"] += (dy / dist) * intensity

                    # Dessine un effet visuel temporaire
                    pygame.draw.circle(screen, WHITE, (int(p["x"]), int(p["y"])), p["radius"] + 10, 3)

                    # Réinitialise le timer (explosion aléatoire dans 3 à 6 secondes)
                    p["timer"] = random.randint(180, 360)


            # Collisions avec les bords
            if j["x"] < 0 or j["x"] > 1600:
                j["vx"] = -j["vx"] * restitution
            if j["y"] < 0 or j["y"] > 1000:
                j["vy"] = -j["vy"] * restitution
                
            # Collision avec les autres joueurs
            for k in joueurs:
                if k != j:
                    dx, dy = j["x"] - k["x"], j["y"] - k["y"]
                    dist = sqrt(dx**2 + dy**2)
                    if dist < 10:
                        nx, ny = dx / dist, dy / dist
                        overlap = 10 - dist
                        j["x"] += nx * (overlap / 2)
                        j["y"] += ny * (overlap / 2)
                        k["x"] -= nx * (overlap / 2)
                        k["y"] -= ny * (overlap / 2)
                        vj_n = j["vx"] * nx + j["vy"] * ny
                        vk_n = k["vx"] * nx + k["vy"] * ny
                        if vj_n - vk_n < 0:
                            impulse = (1 + restitution) * (vj_n - vk_n) / 2
                            j["vx"] -= impulse * nx
                            j["vy"] -= impulse * ny
                            k["vx"] += impulse * nx
                            k["vy"] += impulse * ny

            # Dessin
            pygame.draw.circle(trail_surface, j["color"], (int(j["x"]), int(j["y"])), 5)


        screen.blit(trail_surface, (0, 0))
        explosion_radius = int(p["radius"] * (1 + random.uniform(0.2, 0.4)))
        pygame.draw.circle(screen, (255, 255, 255), (int(p["x"]), int(p["y"])), explosion_radius, 2)

        # Dessiner les planètes
        for p in planetes:
            px, py = p["x"], p["y"]
            mass, radius, color = p["mass"], p["radius"], p["color"]
            pygame.draw.circle(screen, color, (px, py), radius)

    pygame.display.update()