package develop.marat.launch;

import java.io.IOException;

public class LaunchExecutor {
    private static final String HOST = "localhost";
    private static final int PORT = 5050;


    public static void main(String[] args) throws IOException, InterruptedException {
        String socketServerHost = HOST;
        int socketServerPort = PORT;
        if(args.length > 1 && !args[0].isEmpty() && Integer.parseInt(args[1])> 0){
            socketServerHost = args[0];
            socketServerPort = Integer.parseInt(args[1]);
        }

        LaunchService launchService = new LaunchService(socketServerHost, socketServerPort);
        launchService.start();

    }
}
