# TP5 : Réseau et stockage Docker
## Bernard Elena

### 1. Introduction

Dans ce TP, paramétrerez des réseaux et du stockage docker pour y déployer des conteneurs. Vous remettrez à l’issue du TP (via un devoir dont la date sera précisée sur Moodle) un CR comportant les instructions nécessaires pour effectuer les déploiements demandés.

### 2. Réseau bridge

**2.1. Création du réseau bridge**  

```Bash
docker network create \
  --driver bridge \
  --subnet 10.0.0.0/8 \
  --gateway 10.0.0.1 \
  nextcloud-net
```

```--driver bridge``` précise qu'on utilise un réseau de type bridge
```--subnet 10.0.0.0/8``` définit la plage réseau comme spécifié dans le TP
```--gateway 10.0.0.1``` définit la passerelle du réseau
```nextcloud-net``` définit le nom du réseau

**2.2 Deploiement des dockers**

```Yaml
services:
    db:
        image: postgres:15-alpine
        container_name: nextcloud-db
        restart: unless-stopped
        hostname: nextcloud-postgres
        # Volume pour la database PostgreSQL
        volumes:
            - db_data:/var/lib/postgresql/data
        environment:
            POSTGRES_DB: database
            POSTGRES_USER: elena
            POSTGRES_PASSWORD: mdp
        networks:
            - nextcloud-net

    app:
        image: nextcloud:apache
        container_name: nextcloud-app
        restart: unless-stopped
        ports:
            - 8081:80 # Mon port 8080 etait deja utilise
        depends_on:
            - db
        # Volume pour la persistance des fichiers Nextcloud
        volumes:
            - nextcloud_data:/var/www/html
        environment:
            POSTGRES_HOST: nextcloud-postgres
            POSTGRES_DB: database
            POSTGRES_USER: elena
            POSTGRES_PASSWORD: mdp
        networks:
            - nextcloud-net

volumes:
    db_data:
    nextcloud_data:

networks:
    nextcloud-net:
        external: true
```

```image: postgres:15-alpine``` et ```image: nextcloud:apache``` sont des images officielles
On assigne un nom au conteneurs
```restart: unless-stopped``` permet de relancer le conteneur sauf s'il est arreté

```volumes:``` permet de mettre en place les volumes :
* ```- db_data:/var/lib/postgresql/data``` : Volume db_data avec le chemin pour PostgreSQL
* ```- nextcloud_data:/var/www/html`` : Volume nextcloud_data vers le chemin pour Nextcloud

Dans **postgres (db)**
```Yaml
environment:
    POSTGRES_DB: database
    POSTGRES_USER: elena
    POSTGRES_PASSWORD: mdp
```
Met en place les variables d'environnement

Dans **nextcloud (app)**
```Yaml
environment:
    POSTGRES_HOST: nextcloud-postgres
    POSTGRES_DB: database
    POSTGRES_USER: elena
    POSTGRES_PASSWORD: mdp
```
Se connecte a la base de données en utilisant les variable et le hostname de la base


```Yaml
volumes:
    db_data:
    nextcloud_data:
```
permet a compose de créer les deux volumes précédement mentionnés

```Yaml
networks:
    nextcloud-net:
        external: true
```
Ratache les deux conteneurs au réseau ```nextcloud-net``` créer en partie 1. ```external: true``` montre a compose qu'il ne faut pas creer le réseau mais en utiliser un externe

## 3. Réseau MACvlan

```Bash
docker network create \
  --driver macvlan \
  --subnet 192.168.1.0/24 \
  --gateway 192.168.1.1 \
  -o parent=eth0 \
  sniff-net
```

```--driver macvlan``` précise qu'on utilise un réseau de type macvlan
```--subnet 192.168.1.0/24``` définit la plage réseau comme spécifié dans le TP
```--gateway 192.168.1.1``` définit la passerelle du réseau
```nextcloud-net``` définit le nom du réseau
```-o parent=eth0 \``` définit l'interface a laquelle le réseau doit se rattacher

```Bash
mkdir -p ./captures
```
On créer un dossier pour les captures

```Bash
docker run -d \
  --name network-sniffer \
  --network sniff-net-macvlan \
  --ip 192.168.1.69 \
  -v /captures:/data \
  kaazing/tcpdump \
  -i eth0 -w /data/capture.pcap
```
On lance un conteneur qui va *sniffer* le reseau
```--network sniff-net-macvlan``` et ```--ip 192.168.1.69``` rattache le conteneur au réseau mcvlan
```-v ./captures:/data``` créer un bind mount pour récupérer le /data dans /captures
```kaazing/tcpdump``` sur Docker hub : "Capture network traffic in Docker or Docker Compose containers using tcpdump for Wireshark analysis" -> pertinent
```kaazing/tcpdump -i eth0 -w /data/capture.pcap``` execute tcdump dans le docker en ecoutant sur eth0 et ecrit dans un fichier de capture

```docker stop network-sniffer``` permet d'arreter le *sniffer* et de récupérer un fichier de capture analysable avec Wireshark