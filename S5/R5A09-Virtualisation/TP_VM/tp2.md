# Compte-rendu : TP2 VirtualBox Elena BERNARD

1.   
   Les systèmes sont mis à jour, car le mode NAT attribue automatiquement les bonnes configurations aux VM

2.   
   Une erreur s’affiche, car les machines ne sont pas sur le même réseau (par défaut, VirtualBox crée un réseau a part pour chaque nouvelle machine)

3.   
   * Adresse réseau : 10.0.2.1  
   * Masque : 255.255.255.0  
   * Adresses des machines : 10.0.2.10, 10.0.2.11, 10.0.2.12  

4.   
   Le navigateur de la VM Xubuntu affiche "Welcome to nginx !"  
   Nous avons configuré un réseau local entre les machines grâce au NAT Network dans VirtualBox, et avons attribué les IP à chaque machine. Elles peuvent désormais se voir entre elles  

5.   
   Le navigateur ne montre rien et charge en boucle, car le réseau n’est pas ouvert. On peut mettre en place une redirection de port  
   Dans les paramètres de VirtualBox, on ajoute une redirection de port comme suit :  
   * Protocole : TCP  
   * Host IP : 127.0.0.1  
   * Host Port : 8080  
   * Guest IP : 10.0.2.11 -> le serveur
   * Guest Port : 80  

6. 
   Les pings ne fonctionnent que depuis le serveur ou Xubuntu vers le routeur quand la cible est sur le même réseau.  
   Dans l’ordre du tableau :  
   * Passe  
   * Échoue  
   * Passe  
   * Échoue  
   * Échoue  
   
   Pour une connectivité totale, il faut activer le routage IP sur le routeur et ajouter les routes sur le serveur et le client Xubuntu.  

7.   
   Tous les tests passent, grâce aux routes mises en place.  

8.   
   Cela fonctionne, car l’hôte et la VM sont sur le même réseau Host-only ; il n’y a donc pas besoin de NAT.  

9.   
   Un autre hôte n’a pas d’accès, car le réseau Host-only n’est visible que par l’hôte local.  
   Il faut ajouter un adaptateur NAT et configurer une redirection de port, permettant à un autre hôte d’accéder au serveur.  

10.   
    Un ping est possible en mode pont, car les VM sont directement connectées au même réseau physique, avec un masque correct (/30) qui inclut les deux IP des VM.