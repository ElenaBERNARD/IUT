import java.io.*;
import java.net.*;

class ServeurJeu {
    private ServerSocket sockConn;
    private int id;
    private ServerData data; // l'objet partagé entre les threads

  public ServerTCP(int port) throws IOException {
    sockConn = new ServerSocket(port);
    data = new ServerData( ... );
    id = 0;
  }

  public void mainLoop() {
    while(true) {
      try {
        Socket sockComm = sockConn.accept();
        id += 1;
        ThreadServer t = new ThreadServer(id, sockComm, data);
        t.start();
      }
      catch(IOException e) { ... }
    }
  }
}