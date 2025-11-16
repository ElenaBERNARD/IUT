import java.net.Socket;
import java.util.*;
import java.io.*;

class ClientHandler implements Runnable {
    private final Socket socket;
    private final int clientId;
    private final HashMap<Integer, ArrayList<Position>> clientPositions;

    public ClientHandler(Socket socket, int clientId, HashMap<Integer, ArrayList<Position>> clientPositions) {
        this.socket = socket;
        this.clientId = clientId;
        this.clientPositions = clientPositions;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintStream ps = new PrintStream(socket.getOutputStream())) {

            System.out.println("Client connecté. ID: " + clientId);
            ps.println(clientId); // on envoie l’ID attribué

            String input;
            while ((input = br.readLine()) != null) {
                if (input.equals("exit")) {
                    System.out.println("Client " + clientId + " déconnecté.");
                    break;
                }

                int reqNumber;
                try {
                    reqNumber = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    ps.println("REQ_ERR");
                    continue;
                }

                // lecture de l’ID client
                input = br.readLine();
                int id;
                try {
                    id = Integer.parseInt(input);
                } catch (Exception e) {
                    ps.println("NOID_ERR");
                    continue;
                }

                if (!clientPositions.containsKey(id)) {
                    ps.println("NOID_ERR");
                    continue;
                }

                String output = "REQ_ERR"; // réponse par défaut
                switch (reqNumber) {
                    case 1 -> { // storepos
                        input = br.readLine();
                        String[] parts = input.split(",");
                        if (parts.length == 3) {
                            try {
                                double x = Double.parseDouble(parts[0]);
                                double y = Double.parseDouble(parts[1]);
                                double z = Double.parseDouble(parts[2]);
                                synchronized (clientPositions) {
                                    clientPositions.get(id).add(new Position(x, y, z));
                                }
                                output = "OK";
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                    case 2 -> { // pathlen
                        double sum = 0.0;
                        ArrayList<Position> positions;
                        synchronized (clientPositions) {
                            positions = clientPositions.get(id);
                        }
                        if (positions.size() > 1) {
                            for (int i = 0; i < positions.size() - 1; i++) {
                                sum += positions.get(i).distanceTo(positions.get(i + 1));
                            }
                        }
                        output = String.valueOf(sum);
                    }
                    case 3 -> { // findpos
                        input = br.readLine();
                        String[] parts = input.split(",");
                        if (parts.length == 4) {
                            try {
                                double prox = Double.parseDouble(parts[0]);
                                double x = Double.parseDouble(parts[1]);
                                double y = Double.parseDouble(parts[2]);
                                double z = Double.parseDouble(parts[3]);
                                Position p = new Position(x, y, z);

                                output = "FALSE";
                                synchronized (clientPositions) {
                                    for (Position pos : clientPositions.get(id)) {
                                        if (pos.distanceTo(p) <= prox) {
                                            output = "TRUE";
                                            break;
                                        }
                                    }
                                }
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    }
                    case 4 -> { // dénivelé
                        double denivpos = 0.0;
                        double denivneg = 0.0;
                        ArrayList<Position> positions;
                        synchronized (clientPositions) {
                            positions = clientPositions.get(id);
                        }
                        if (positions.size() > 1) {
                            for (int i = 0; i < positions.size() - 1; i++) {
                                double dist = positions.get(i).getY() - positions.get(i + 1).getY();
                                if (dist > 0)
                                    denivpos += dist;
                                else
                                    denivneg += dist;
                            }
                        }
                        output = "Denivele + : " + denivpos + ", Denivele - : " + denivneg;
                    }
                    case 5 -> { // dernières positions des autres clients
                        StringBuilder response = new StringBuilder();
                        synchronized (clientPositions) {
                            for (Integer otherId : clientPositions.keySet()) {
                                if (otherId != id && !clientPositions.get(otherId).isEmpty()) {
                                    Position lastPos = clientPositions.get(otherId)
                                            .get(clientPositions.get(otherId).size() - 1);
                                    response.append("Client ").append(otherId)
                                            .append(": ").append(lastPos.toString()).append(", ");
                                }
                            }
                        }
                        output = response.isEmpty() ? "Aucune position trouvée" : response.toString();
                    }
                    default -> {
                        // REQ_ERR reste par défaut
                    }
                }

                ps.println(output);
            }

        } catch (IOException e) {
            System.out.println("Erreur client " + clientId + " : " + e.getMessage());
        }
    }
}