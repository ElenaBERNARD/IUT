import java.io.*;
import java.net.*;
import java.util.ArrayList;

class PositionServeur {

    public static void main(String[] args) {

        BufferedReader br = null; // pour lire du texte sur la socket
        PrintStream ps = null; // pour envoyer du texte sur la socket
        String line = ""; // la ligne reçu/envoyée

        ArrayList<Position> Positions = new ArrayList<Position>();

        ServerSocket conn = null;
        Socket sock = null;
        int port = -1;

        if (args.length != 1) {
            System.out.println("usage: PositionServer port");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]); // récupération du port sous forme int
            conn = new ServerSocket(port); // création socket serveur
        } catch (IOException e) {
            System.out.println("problème création socket serveur : " + e.getMessage());
            System.exit(1);
        }

        while (line != null && !line.equals("exit")) {
            try {
                sock = conn.accept(); // attente connexion client
                br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // creation flux lecture lignes
                                                                                       // de textes
                ps = new PrintStream(sock.getOutputStream()); // création flux écriture lignes de texte

                while (true) {
                    line = br.readLine(); // réception d'une ligne
                    if (line == null) { // le client s’est déconnecté (CTRL+D côté client ou fermeture socket)
                        System.out.println("Client déconnecté.");
                        line = "";
                        break;
                    }

                    if (line.isEmpty()) { // ligne vide reçue
                        System.out.println("Client a envoyé une ligne vide. Fermeture connexion.");
                        break;
                    }

                    if (line.equals("exit")) { // ligne "exit" reçue
                        System.out.println("Client a envoyé 'exit'. Fermeture du serveur.");
                        break;
                    }

                    try {
                        Integer.parseInt(line);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrée invalide, veuillez entrer un numéro de requette valide.");
                        line = "REQ_ERR";
                        ps.println(line); // envoi de l'erreur
                        continue;
                    }

                    int reqNumber = Integer.parseInt(line);
                    switch (reqNumber) {
                        case 1: {
                            System.out.println("Réception des positions");
                            // Réception des positions
                            line = br.readLine(); // réception des valeurs

                            String[] parts = line.split(",");
                            if (parts.length != 3) {
                                System.out.println(
                                        "Entrée invalide, veuillez entrer des coordonnées sous le format 'x,y,z'.");
                                line = "REQ_ERR";
                                ps.println(line); // envoi de l'erreur
                                continue;
                            }

                            try {
                                double x = Double.parseDouble(parts[0]);
                                double y = Double.parseDouble(parts[1]);
                                double z = Double.parseDouble(parts[2]);
                                Positions.add(new Position(x, y, z));
                            } catch (NumberFormatException e) {
                                System.out.println(
                                        "Entrée invalide, veuillez entrer des doubles sous le format 'x,y,z'.");
                                line = "REQ_ERR";
                                ps.println(line); // envoi de l'erreur
                                continue;
                            }

                            line = "OK"; // Accusé de réception
                            break;
                        }
                        case 2: {
                            System.out.println("Calcul de la distance totale");
                            
                            // Calcul de la distance totale
                            double sum = 0.0;
                            if (Positions.size() > 1) {
                                for (int i = 0; i < Positions.size() - 1; i++) {
                                    sum += Positions.get(i).distanceTo(Positions.get(i + 1));
                                }
                            }
                            
                            line = String.valueOf(sum);
                            break;
                        }
                        case 3: {
                            System.out.println("Vérification de la proximité");
                            
                            // Vérification de la proximité
                            line = br.readLine();
                            
                            String[] parts = line.split(",");
                            if (parts.length != 4) {
                                System.out.println(
                                        "Entrée invalide, veuillez entrer des coordonnées sous le format 'x,y,z,p'.");
                                line = "REQ_ERR";
                                ps.println(line); // envoi de l'erreur
                                continue;
                            }
                            
                            Position p;
                            double prox;
                            try {
                                double x = Double.parseDouble(parts[1]);
                                double y = Double.parseDouble(parts[2]);
                                double z = Double.parseDouble(parts[3]);
                                prox = Double.parseDouble(parts[0]);
                                p = new Position(x, y, z);
                            } catch (NumberFormatException e) {
                                System.out.println(
                                        "Entrée invalide, veuillez entrer des coordonnées sous le format 'p,x,y,z'.");
                                line = "REQ_ERR";
                                ps.println(line); // envoi de l'erreur
                                continue;
                            }

                            line = "FALSE";
                            // Vérification si une position est dans la proximite
                            for (Position pos : Positions) {
                                System.out.println("Distance to check: " + pos.distanceTo(p) + " vs " + prox);
                                if (pos.distanceTo(p) <= prox) {
                                    line = "TRUE";
                                    break;
                                }
                            }

                            break;
                        }
                        default: {
                            System.out.println(
                                    "Numéro de requette invalide, veuillez entrer un numéro de requette valide.");
                            line = "REQ_ERR";
                            break;
                        }
                    }

                    ps.println(line);
                }

                br.close();
                ps.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }
}