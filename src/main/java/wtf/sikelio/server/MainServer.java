package wtf.sikelio.server;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                printUsage();
            } else {
                Integer port = new Integer(args[0]);
                Server server = new Server(port);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printUsage() {
        System.out.println("java server.Server <port>");
        System.out.println("\t<port>: server's port");
    }
}
