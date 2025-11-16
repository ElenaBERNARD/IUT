##Modules
import sys
import pygame
from numpy import *

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
    
    

        

    #Mise Ã  jour de l'Ã©cran
    pygame.display.update()
