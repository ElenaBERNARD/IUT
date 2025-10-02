##Modules
import sys
import pygame
import numpy as np
from math import *

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
global keyup,keydown,keyright,keyleft,keyspace, keyR
keyup,keydown,keyright,keyleft,keyspace,keyR=False,False,False,False,False,False


# booleens gestion du jeu 
start = False

##Fenêtre
pygame.init()
screen = pygame.display.set_mode((1600,1000))

#Temps
clock = pygame.time.Clock()

#------------------------------ TRANSFORMATION ----------------------------------

def zoom(P, rapport):
  z = np.array([[rapport, 0, 0],
                [0, rapport, 0],
                [0, 0, rapport]])
  return np.dot(z,P)

def translation(P, x, y, z):
  n = len(P[0])
  for i in range(n):
    P[0][i] += x
    P[1][i] += y
    P[2][i] += z
  return P

def rotx(P, angle):
  angle=np.radians(angle)
  
  r = np.array([[1, 0, 0],
                [0, np.cos(angle), -np.sin(angle)],
                [0, np.sin(angle), np.cos(angle)]])
  return np.dot(r,P)

def roty(P, angle):
  angle=np.radians(angle)
  
  r = np.array([[np.cos(angle), 0, np.sin(angle)],
                [0, 1, 0],
                [-np.sin(angle), 0, np.cos(angle)]])
  return np.dot(r,P)

def rotz(P, angle):
  angle=np.radians(angle)
  
  r = np.array([[np.cos(angle), -np.sin(angle), 0],
                [np.sin(angle), np.cos(angle), 0],
                [0, 0, 1]])
  return np.dot(r,P)

def stretch(P, rx, ry, rz):
  s = np.array([[rx, 0, 0],
                [0, ry, 0],
                [0, 0, rz]])
  return np.dot(s,P)

def symY(P):
  s = np.array([[-1, 0, 0],
                [0, 1, 0],
                [0, 0, -1]])
  return np.dot(s, P)

def dessiner(P,A):
  n = len(P[0])
  for i in range(n):
    for j in range(i+1,n):
      if A[i][j]:
        pygame.draw.line(screen, GREEN, (P[0][i], P[1][i]), (P[0][j], P[1][j]), 2)

#------------------------------ CUBE ----------------------------------

# Pcube = np.array([[-1,-1,-1,-1,1,1,1,1],
#                   [-1,-1,1,1,-1,-1,1,1],
#                   [-1,1,-1,1,-1,1,-1,1]])

# n = len(Pcube[0])

# Acube = np.array([[False]*n]*n)

# for i in range(n):
#   for j in range(n):
#     if (Pcube[0][j] - Pcube[0][i])**2 + (Pcube[1][j] - Pcube[1][i])**2 + (Pcube[2][j] - Pcube[2][i])**2 <= 4.1 and i!=j:
#       Acube[i][j] = True
      
# Pcube = stretch(Pcube, 2, 0.5, 1)
# Pcube = zoom(Pcube, 50)    
# Pcube = translation(Pcube, 800, 500, 0)
    
# print(Pcube)
# # print(Acube)
        
#------------------------------ COLINE ----------------------------------
nCol = 961
Pcol = np.array([[0]*nCol]*3)

c = 0
for x in range(-15,16, 1):
  for z in range(-15,16, 1):
      Pcol[0][c] = x
      Pcol[2][c] = z
      Pcol[1][c] = sqrt(-(x)**2 - z**2 + 290**2)
      c+=1

Acol = np.array([[False]*nCol]*nCol)

for i in range(nCol):
  for j in range(nCol):
    if (Pcol[0][j] - Pcol[0][i])**2 + (Pcol[1][j] - Pcol[1][i])**2 + (Pcol[2][j] - Pcol[2][i])**2 <= 4 and i!=j:
      Acol[i][j] = True
      
Pcol = zoom(Pcol, 3)

#------------------------------ MAIN ----------------------------------

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
            if event.key == pygame.K_r:
              keyR=True
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
            if event.key == pygame.K_r:
              keyR=False

              
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
      t+= 1/60
      Pcol = roty(Pcol, 1)
      Pcol = rotx(Pcol, 0.1)
      Pcol = translation(Pcol, 800, 500, 0)
      # Pcube = translation(Pcube, -800, -500, 0)
      
      # Pcube = zoom(Pcube, 1 + 0.005*np.sin(t))
      
      # Pcube = rotx(Pcube, 1)
      # Pcube = roty(Pcube, np.cos(t))
      # Pcube = rotz(Pcube, -1)
      
      
      # Pcube = translation(Pcube, 800, 500, 0)

      
      # Pcube2 = np.copy(Pcube)
      # Pcube2 = translation(Pcube2, -1000, 0, 0)
      # Pcube2 = symY(Pcube2)
      # Pcube2 = translation(Pcube2, 1000, 0, 0)
      
      # dessiner(Pcube, Acube)
      # dessiner(Pcube2, Acube)
      dessiner(Pcol, Acol)
      Pcol = translation(Pcol, -800, -500, 0)
      



    #Mise Ã  jour de l'Ã©cran
    pygame.display.update()