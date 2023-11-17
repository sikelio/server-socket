package wtf.sikelio.client;

import wtf.sikelio.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive implements Runnable {
    private Client _client;
    private Socket _socket;
    private ObjectInputStream _in;

    public ClientReceive(Client client, Socket socket) {
        this._client = client;
        this._socket = socket;
    }

    @Override
    public void run() {
        try {
            this._in = new ObjectInputStream(this._socket.getInputStream());

            boolean isActive = true;

            while (isActive) {
                Message message = (Message) this._in.readObject();

                if (message != null) {
                    this._client.messageReceived(message);
                } else {
                    isActive = false;
                }
            }

            this._client.disconnectedServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
