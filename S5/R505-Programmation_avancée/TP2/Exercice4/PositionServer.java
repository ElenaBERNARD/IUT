import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

class PositionServeur {

    public static void main(String[] args) {

        BufferedReader br = null;
        PrintStream ps = null;
        String input;   // lecture client
        String output;  // réponse serveur
        int globalId = 0;
        int clientId = -1;

        HashMap<Integer, ArrayList<Position>> clientPositions = new HashMap<>();

        ServerSocket conn = null;
        Socket sock = null;
        int port = -1;

        if (args.length != 1) {
            System.out.println("usage: PositionServer port");
            System.exit(1);
        }

        try {
            port = Integer.parseInt(args[0]);
            conn = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("problème création socket serveur : " + e.getMessage());
            System.exit(1);
        }

        while (true) {
            try {
                sock = conn.accept();
                br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                ps = new PrintStream(sock.getOutputStream());

                System.out.println("Client connecté. ID: " + globalId);
                ps.println(globalId);
                clientPositions.put(globalId, new ArrayList<Position>());
                globalId++;

                while (true) {
                    input = br.readLine();
                    if (input == null) {
                        System.out.println("Client déconnecté.");
                        break;
                    }

                    if (input.isEmpty()) {
                        System.out.println("Client a envoyé une ligne vide. Fermeture connexion.");
                        break;
                    }

                    if (input.equals("exit")) {
                        System.out.println("Client a envoyé 'exit'. Fermeture du serveur.");
                        break;
                    }

                    int reqNumber;
                    try {
                        reqNumber = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        ps.println("REQ_ERR");
                        continue;
                    }

                    // Lecture de l'ID client
                    input = br.readLine();
                    if (input == null)
                        break;

                    clientId = -1;
                    try {
                        clientId = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        ps.println("NOID_ERR");
                        continue;
                    }

                    if (!clientPositions.containsKey(clientId)) {
                        ps.println("NOID_ERR");
                        continue;
                    }

                    output = "REQ_ERR"; // valeur par défaut
                    switch (reqNumber) {
                        case 1: {
                            System.out.println("Réception des positions");
                            input = br.readLine();
                            String[] parts = input.split(",");
                            if (parts.length == 3) {
                                try {
                                    double x = Double.parseDouble(parts[0]);
                                    double y = Double.parseDouble(parts[1]);
                                    double z = Double.parseDouble(parts[2]);
                                    clientPositions.get(clientId).add(new Position(x, y, z));
                                    output = "OK";
                                } catch (NumberFormatException e) {
                                    output = "REQ_ERR";
                                }
                            }
                            break;
                        }
                        case 2: {
                            System.out.println("Calcul de la distance totale");
                            double sum = 0.0;
                            ArrayList<Position> positions = clientPositions.get(clientId);
                            if (positions.size() > 1) {
                                for (int i = 0; i < positions.size() - 1; i++) {
                                    sum += positions.get(i).distanceTo(positions.get(i + 1));
                                }
                            }
                            output = String.valueOf(sum);
                            break;
                        }
                        case 3: {
                            System.out.println("Vérification de la proximité");
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
                                    for (Position pos : clientPositions.get(clientId)) {
                                        if (pos.distanceTo(p) <= prox) {
                                            output = "TRUE";
                                            break;
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    output = "REQ_ERR";
                                }
                            }
                            break;
                        }
                        case 4: {
                            System.out.println("Calcul des deniveles");
                            double denivpos = 0.0;
                            double denivneg = 0.0;
                            ArrayList<Position> positions = clientPositions.get(clientId);
                            if (positions.size() > 1) {
                                for (int i = 0; i < positions.size() - 1; i++) {
                                    double dist = positions.get(i).getY() - positions.get(i + 1).getY();
                                    if (dist > 0) {
                                        denivpos += dist;
                                    } else {
                                        denivneg += dist;
                                    }
                                }
                            }
                            output = "Denivele + : " + denivpos + ", Denivele - : " + denivneg;
                            break;
                        }
                        case 5: {
                            System.out.println("Recherche des dernières positions des autres clients");
                            StringBuilder response = new StringBuilder();
                            for (Integer id : clientPositions.keySet()) {
                                ArrayList<Position> positions = clientPositions.get(id);
                                if (id != clientId && !positions.isEmpty()) {
                                    Position lastPos = positions.get(positions.size() - 1);
                                    response.append("Client ").append(id).append(": ").append(lastPos.toString())
                                            .append(", ");
                                }
                            }
                            output = response.toString().isEmpty() ? "Aucune position trouvée" : response.toString();
                            break;
                        }
                        default: {
                            System.out.println("Numéro de requête invalide");
                            output = "REQ_ERR";
                        }
                    }

                    ps.println(output);
                }

                br.close();
                ps.close();
                sock.close();
                if(input != null && input.equals("exit")) {
                    System.out.println("Fermeture du serveur.");
                    break;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            if (conn != null)
                conn.close();
        } catch (Exception e) {
            System.exit(1);
        }
    }
}