import java.io.*;
import java.net.*;
import java.util.Scanner;

class PositionClient {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BufferedReader br = null; // pour lire du texte sur la socket
        PrintStream ps = null;    // pour écrire du texte sur la socket
        int clientId = -1;

        Socket sock = null;
        int port = -1;

        if (args.length != 2) {
            System.out.println("usage: PositionClient ip_server port");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[1]);
            sock = new Socket(args[0], port);
        } catch (IOException e) {
            System.out.println("problème de connexion au serveur : " + e.getMessage());
            System.exit(1);
        }

        try {
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            ps = new PrintStream(sock.getOutputStream());

            System.out.println("Connexion au serveur en cours...");
            clientId = Integer.parseInt(br.readLine()); // première ligne envoyée par le serveur = ID
            System.out.println("Connecté au serveur avec l'id: " + clientId);

            while (true) {
                System.out.print("> ");
                if (!sc.hasNextLine()) {
                    break; // EOF ou CTRL+D
                }
                String input = sc.nextLine().trim();

                if (input.isEmpty()) continue;

                // Quitter proprement
                if (input.equalsIgnoreCase("exit")) {
                    ps.println("exit");
                    String resp = br.readLine();
                    System.out.println("Server: " + resp);
                    break;
                }

                // --- Parsing des commandes utilisateur ---
                if (input.startsWith("storepos ")) {
                    // Format attendu : storepos x,y,z
                    String coords = input.substring("storepos".length()).trim();
                    if (!coords.matches("^-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?$")) {
                        System.out.println("Format invalide. Utilise : storepos x,y,z");
                        continue;
                    }

                    // Envoi au serveur
                    ps.println("1"); // numéro de requête
                    ps.println(clientId); // ID
                    ps.println(coords); // coordonnées

                } else if (input.equals("pathlen")) {
                    // Envoi au serveur
                    ps.println("2"); // numéro de requête
                    ps.println(clientId); // ID

                } else if (input.startsWith("findpos ")) {
                    // Format attendu : findpos p,x,y,z
                    String params = input.substring("findpos".length()).trim();
                    if (!params.matches("^-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?,-?\\d+(\\.\\d+)?$")) {
                        System.out.println("Format invalide. Utilise : findpos p,x,y,z");
                        continue;
                    }

                    // Envoi au serveur
                    ps.println("3"); // numéro de requête
                    ps.println(clientId); // ID
                    ps.println(params); // facteur + coordonnées

                } else {
                    System.out.println("Commande inconnue. Commandes valides :");
                    System.out.println("  storepos x,y,z");
                    System.out.println("  pathlen");
                    System.out.println("  findpos p,x,y,z");
                    System.out.println("  exit");
                    continue;
                }

                // Lecture de la réponse
                String resp = br.readLine();
                if (resp == null) {
                    System.out.println("Connexion fermée par le serveur.");
                    break;
                }

                switch (resp) {
                    case "REQ_ERR":
                        System.out.println("Erreur: format de requête invalide.");
                        break;
                    case "NOID_ERR":
                        System.out.println("Erreur: ID client invalide.");
                        break;
                    default:
                        System.out.println("Server: " + resp);
                        break;
                }
            }

            br.close();
            ps.close();
            sock.close();

        } catch (IOException e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        sc.close();
    }
}
