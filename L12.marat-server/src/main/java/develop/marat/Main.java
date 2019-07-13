package develop.marat;

import develop.marat.server.Server;

public class Main {
    public static void main(String[] args) {
        int port = 8080;
        new Server(port).start();
    }
}
