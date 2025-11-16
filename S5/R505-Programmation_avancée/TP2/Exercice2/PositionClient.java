import java.io.*;
import java.net.*;
import java.util.Scanner;

class PositionClient {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BufferedReader br = null; // pour lire du texte sur la socket
        PrintStream ps = null; // pour écrire du texte sur la socket
        String message = null;
        int clientId = -1;

        String line = null;
        Socket sock = null;
        int port = -1;

        if (args.length != 2) {
            System.out.println("usage: PositionClient ip_server port");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[1]); // récupération du port sous forme int
            sock = new Socket(args[0], port); // création socket client et connexion au serveur donné en args[0]
        } catch (IOException e) {
            System.out.println("problème de connexion au serveur : " + e.getMessage());
            System.exit(1);
        }
        try {
            br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // création flux lecture lignes de
                                                                                   // texte
            ps = new PrintStream(sock.getOutputStream()); // création flux écriture lignes de texte

            System.out.println("Connexion au serveur en cours...");
            clientId = Integer.parseInt(br.readLine());
            System.out.println("Connecté au serveur avec l'id: " + clientId);

            while (true) {
                System.out.print("Entrez un message: ");
                try {
                    if (!sc.hasNextLine()) { // détecte CTRL+D (EOF)
                        break;
                    }
                    message = sc.nextLine();
                } catch (Exception e) {
                    break;
                }

                // Logique pour gérer les commandes spéciales
                if (message.isEmpty() || message.equalsIgnoreCase("exit")) {
                    ps.println(message);

                    line = br.readLine(); // réponse serveur
                    if (line == null) {
                        System.out.println("Connexion fermée par le serveur.");
                        break;
                    }
                    System.out.println("Server: " + line);

                    if (message.equalsIgnoreCase("exit")) {
                        break; // on arrête aussi côté client
                    }

                } else {
                    System.out.println("Envoi de la requette : " + message);
                    ps.println(message); // Envoi du numéro de requête
                    ps.println(clientId); // Envoi de l'ID client

                    // Si la requête est 1 ou 3 on attend une deuxième ligne de l'utilisateur
                    if (message.equals("1")) {
                        System.out.print("Entrez les coordonnées x,y,z : ");
                        String coords = sc.nextLine();
                        ps.println(coords); // envoi des coordonnées
                    } else if (message.equals("3")) {
                        System.out.print("Entrez p,x,y,z : ");
                        String params = sc.nextLine();
                        ps.println(params); // envoi du facteur + coordonnées
                    }

                    // Lecture de la réponse du serveur
                    line = br.readLine();
                    if (line == null) {
                        System.out.println("Connexion fermée par le serveur.");
                        break;
                    } else if (line.equals("REQ_ERR")) {
                        System.out.println("Format de requête invalide.");
                    } else {
                        System.out.println("Server : " + line);
                    }
                }
            }

            br.close();
            ps.close();
        } catch (

        IOException e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}