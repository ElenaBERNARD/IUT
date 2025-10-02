import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

class PositionServeur {
    private static int globalId = 0;
    private static final HashMap<Integer, ArrayList<Position>> clientPositions = new HashMap<>();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: PositionServer port");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur démarré sur le port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // on accepte un client
                synchronized (PositionServeur.class) {
                    clientPositions.put(globalId, new ArrayList<>());
                }

                // Lancer un nouveau thread pour ce client
                Thread t = new Thread(new ClientHandler(clientSocket, globalId, clientPositions));
                t.start();

                globalId++;
            }
        } catch (IOException e) {
            System.out.println("Erreur serveur: " + e.getMessage());
        }
    }
}