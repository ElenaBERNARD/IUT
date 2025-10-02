import java.io.*;
import java.net.*;
import java.util.Scanner;

class SommeClient {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        BufferedReader br = null; // pour lire du texte sur la socket
        PrintStream ps = null; // pour écrire du texte sur la socket
        String message = null;
        boolean validInput;
        String[] numbers = null;

        String line = null;
        Socket sock = null;
        int port = -1;

        if (args.length != 2) {
            System.out.println("usage: EchoClient ip_server port");
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
            br = new BufferedReader(new InputStreamReader(sock.getInputStream())); // création flux lecture lignes de texte
            ps = new PrintStream(sock.getOutputStream()); // création flux écriture lignes de texte

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
                    System.out.println("le serveur me répond : " + line);

                    if (message.equalsIgnoreCase("exit")) {
                        break; // on arrête aussi côté client
                    }

                } else {
                    validInput = true;
                    numbers = message.split(",");

                    for (String num : numbers) {
                        try {
                            Integer.parseInt(num);
                        } catch (NumberFormatException e) {
                            validInput = false;
                            break;
                        }
                    }

                    if (validInput) {
                        // Envoi de la taille de la liste au serveur
                        ps.println(numbers.length); 
                        for (String num : numbers) {
                            ps.println(num); // Envoi de chaque nombre au serveur
                        }

                        line = br.readLine(); // Lecture réponse serveur
                        
                        // Si le serveur ferme la connexion
                        if (line == null) {
                            System.out.println("Connexion fermée par le serveur.");
                            break;
                        } 
                        // Si il y a eu une erreur dans la requette
                        else if (line.equals("REQ_ERR")) {
                            System.out.println("Format de requête invalide. Veuillez envoyer une liste de nombres séparés par des virgules.");
                        } else {
                            System.out.println("le serveur me repond : " + line);
                        }
                    } else {
                        System.out.println("Entrée invalide, veuillez entrer des nombres séparés par des virgules.");
                    }
                }
            }

            br.close();
            ps.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}