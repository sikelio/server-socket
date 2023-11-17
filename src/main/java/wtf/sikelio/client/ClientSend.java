package wtf.sikelio.client;

import wtf.sikelio.common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSend implements Runnable {
    private Socket _socket;
    private ObjectOutputStream _out;

    public ClientSend(Socket socket, ObjectOutputStream out) {
        this._socket = socket;
        this._out = out;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Your message >> ");

            String inputMessage = scanner.nextLine();

            Message message = new Message("client", inputMessage);

            try {
                this._out.writeObject(message);
                this._out.flush();

                if (inputMessage.equals("exit")) {
                    this._out.close();
                    this._socket.close();

                    System.exit(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
