package wtf.sikelio.client;

import wtf.sikelio.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String _address;
    private Integer _port;
    private Socket _socket;
    private ObjectInputStream _in;
    private ObjectOutputStream _out;

    public Client(String address, Integer port) {
        this._address = address;
        this._port = port;

        try {
            this._socket = new Socket(this._address, this._port);
            this._out = new ObjectOutputStream(this._socket.getOutputStream());

            Thread clientSendThread = new Thread(new ClientSend(this._socket, this._out));
            Thread clientReceiveThread = new Thread(new ClientReceive(this, this._socket));

            clientSendThread.start();
            clientReceiveThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnectedServer() {
        try {
            if (this._in != null) {
                this._in.close();
            }

            this._out.close();
            this._socket.close();

            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void messageReceived(Message message) {
        System.out.println(message.toString());
    }
}
