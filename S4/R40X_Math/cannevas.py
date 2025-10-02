##Modules
import sys
import pygame
import numpy as np

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

#-----------------Conditions initiales (au tout début du jeu)---------------
#------------------------------À COMPLÉTER----------------------------------

global t
t=0

   
#booleens de clavier
global keyup,keydown,keyright,keyleft,keyspace 
keyup,keydown,keyright,keyleft,keyspace=False,False,False,False,False


# booleens gestion du jeu 
start = False

##Fenêtre
pygame.init()
screen = pygame.display.set_mode((1600,1000))

#Temps
clock = pygame.time.Clock()

# Cube
Pcube = np.array([[-1, -1, -1, -1, 1, 1, 1, 1],
                  [-1, -1, 1, 1, -1, -1, 1, 1],
                  [-1, 1, -1, 1, -1, 1, -1, 1]])

Acube=np.array([[False]*8]*8)

for i in range(8):
  for j in range(8):
    if (Pcube[0][j] - Pcube[0][i])**2 + (Pcube[1][j] - Pcube[1][i])**2 + (Pcube[2][j] - Pcube[2][i])**2 <= 4.1 and i != j:
      Acube[i][j] = True

print(Acube)

def dessiner_cube(cube):
  for i in range(8):
    for j in range(i+1, 8):
      if Acube[i][j]:
        pygame.draw.line(screen, WHITE, (cube[0][i], cube[1][i]), (cube[0][j], cube[1][j]))

def zoom(cube, ratio):
  z = np.array([[ratio, 0, 0],
                [0, ratio, 0],
                [0, 0, ratio]])
  return np.dot(z, cube)

def translate(cube, x, y, z):
  for i in range(8):
    cube[0][i] += x
    cube[1][i] += y
    cube[2][i] += z
  return cube

def rotx(cube, angle):
  angle = np.radians(angle)
  r = np.array([
    [1, 0, 0],
    [0, np.cos(angle), -np.sin(angle)],
    [0, np.sin(angle), np.cos(angle)]])
  return np.dot(r, cube)

def roty(cube, angle):
  angle = np.radians(angle)
  r = np.array([
    [np.cos(angle), 0, np.sin(angle)],
    [0, 1, 0],
    [-np.sin(angle), 0, np.cos(angle)]])
  return np.dot(r, cube)

def rotz(cube, angle):
  angle = np.radians(angle)
  r = np.array([
    [np.cos(angle), -np.sin(angle), 0],
    [np.sin(angle), np.cos(angle), 0],
    [0, 0, 1]])
  return np.dot(r, cube)


##Boucle principale
while True:
    #Gestion du temps
    clock.tick(60)
    t+=1/60

    #Gestion des Ã©vÃ©nements
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
              pygame.quit()
            if event.key == pygame.K_SPACE:
              keyspace=True
            if event.key == pygame.K_UP:
              keyup=True
            if event.key == pygame.K_DOWN:
              keydown=True
            if event.key == pygame.K_RIGHT:
              keyright=True
            if event.key == pygame.K_LEFT:
              keyleft=True
        if event.type == pygame.KEYUP:
            if event.key == pygame.K_SPACE:
              keyspace=False
            if event.key == pygame.K_UP:
              keyup=False
            if event.key == pygame.K_DOWN:
              keydown=False
            if event.key == pygame.K_RIGHT:
              keyright=False
            if event.key == pygame.K_LEFT:
              keyleft=False

              
    #Clearscreen
    screen.fill(BLACK)

    #Create text and text rectangle
    font = pygame.font.Font('freesansbold.ttf', 32)
    text = font.render('To start press space', True, WHITE, BLACK)
    textRect = text.get_rect()
    textRect.center = (800, 500)


#----------------------- Jeu -----------------------------

    #Démarrage
    if not start:
        t = 0
        #Affichage du texte
        screen.blit(text, textRect)      
    
    if keyspace and not start:
        start = True
    
    
    if start:
      cube = Pcube
      # TODO: Mettre ici le code du jeu
      dessiner_cube(cube)
      if keyup:
        cube = roty(cube, 1)
      if keydown:
        cube = roty(cube, -1)
      if keyright:
        cube = rotx(cube, 1)
      if keyleft:
        cube = rotx(cube, -1)
      
      cube = zoom(cube, 100)
      cube = translate(cube, 800, 500, 0)
        
        

    #Mise Ã  jour de l'Ã©cran
    pygame.display.update()
