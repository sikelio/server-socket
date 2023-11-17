package wtf.sikelio.server;

import wtf.sikelio.common.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private Integer _port;
    private List<ConnectedClient> _clients;

    public Server(Integer port) throws IOException {
        this._port = port;
        this._clients = new ArrayList<>();

        Thread threadConnection =  new Thread(new Connection(this));

        threadConnection.start();
    }

    public Integer getPort() {
        return this._port;
    }

    public Integer getNumClients() {
        return this._clients.size();
    }

    public void addClient(ConnectedClient newClient) {
        this._clients.add(newClient);

        Message message = new Message("server", newClient.getId() + " just connected");

        this.broadcastMessage(message, newClient.getId());
    }

    public void broadcastMessage(Message message, Integer id) {
        for (ConnectedClient client : this._clients) {
            if (client.getId() != id) {
                client.sendMessage(message);
            }
        }
    }

    public void disconnectedClient(ConnectedClient discClient) {
        discClient.closeClient();

        this._clients.removeIf(e -> e.getId().equals(discClient.getId()));

        Message message = new Message("server", discClient.getId() + " just disconnected");

        this.broadcastMessage(message, discClient.getId());
    }
}
