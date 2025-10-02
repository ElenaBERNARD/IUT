import java.io.*;
import java.net.*;

class EchoServeur {

    public static void main(String[] args) {

        BufferedReader br = null; // pour lire du texte sur la socket
        PrintStream ps = null; // pour envoyer du texte sur la socket
        String line = ""; // la ligne reçu/envoyée
        ServerSocket conn = null;
        Socket sock = null;
        int port = -1;

        if (args.length != 1) {
            System.out.println("usage: Server port");
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
                                                                                       // de
                                                                                       // textes
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

                    if(line.equals("exit")) { // ligne "exit" reçue
                        System.out.println("Client a envoyé 'exit'. Fermeture du serveur.");
                        break;
                    }

                    System.out.println("le client me dit : " + line); // affichage debug
                    ps.println(line); // envoi de la ligne précédemment reçue
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