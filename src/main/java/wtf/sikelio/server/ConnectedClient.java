package wtf.sikelio.server;

import wtf.sikelio.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private static Integer _idCounter = 0;
    private Integer _id;
    private Server _server;
    private Socket _socket;
    private ObjectOutputStream _out;
    private ObjectInputStream _in;

    public ConnectedClient(Server server, Socket socket) {
        this._server = server;
        this._socket = socket;
        this._id = _idCounter;

        ConnectedClient._idCounter++;

        try {
            this._out = new ObjectOutputStream(this._socket.getOutputStream());

            System.out.println("New connection, id: " + this._id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            this._in = new ObjectInputStream(this._socket.getInputStream());

            boolean isActive = true;

            while (isActive) {
                Message message = (Message) this._in.readObject();

                if (message != null) {
                    message.setSender(String.valueOf(this._id));

                    this._server.broadcastMessage(message, this._id);
                } else {
                    this._server.disconnectedClient(this);

                    isActive = false;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();


        }
    }

    public void sendMessage(Message message) {
        try {
            this._out.writeObject(message);
            this._out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return this._id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public void closeClient() {
        try {
            this._in.close();
            this._out.close();
            this._socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
