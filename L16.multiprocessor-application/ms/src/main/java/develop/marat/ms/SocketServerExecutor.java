package develop.marat.ms;

import develop.marat.ms.core.SocketMessageSystem;

import java.io.IOException;

public class SocketServerExecutor {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        SocketMessageSystem socketServer = new SocketMessageSystem(port);
        socketServer.start();
    }
}
