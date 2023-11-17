package wtf.sikelio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {
    private Server _server;
    private ServerSocket _serverSocket;

    public Connection(Server server) {
        this._server = server;

        try {
            this._serverSocket = new ServerSocket(server.getPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                Socket socketNewClient = this._serverSocket.accept();
                ConnectedClient newClient = new ConnectedClient(this._server, socketNewClient);

                newClient.setId(this._server.getNumClients());

                this._server.addClient(newClient);

                Thread threadNewClient = new Thread(newClient);

                threadNewClient.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
