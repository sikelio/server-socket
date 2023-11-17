package wtf.sikelio.client;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainClient {
    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
        } else {
            String address = args[0];
            Integer port = new Integer(args[1]);
            Client c = new Client(address, port);
        }
    }

    private static void printUsage() {
        System.out.println("java client.Client <address> <port>");
        System.out.println("\t<address>: server's ip address");
        System.out.println("\t<port>: server's port");
    }
}
