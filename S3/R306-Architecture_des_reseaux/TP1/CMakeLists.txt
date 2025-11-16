cmake_minimum_required(VERSION 3.29)
project(TP1_client_serveur C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(TP1_client_serveur
    client.c
    client_serveur.h
    serveur_parallele.c
    serveur_sequentiel.c)
