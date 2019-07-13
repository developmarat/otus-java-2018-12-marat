package develop.marat.launch;

import develop.marat.launch.runner.ProcessRunner;
import develop.marat.launch.runner.ProcessRunnerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LaunchService {
    private static final int PROCESSES_COUNT = 5;

    private static final String MS_SOCKET_SERVER_START_COMMAND = "java -jar ../../ms/target/ms.jar ";
    private static final String BACKEND_FIRST_START_COMMAND = "java -jar ../../backend/target/backend.jar ";
    private static final String BACKEND_SECOND_START_COMMAND = "java -jar ../../backend/target/backend.jar ";
    private static final String FRONTEND_FIRST_START_COMMAND = "java -jar ../../frontend/target/frontend.jar ";
    private static final String FRONTEND_SECOND_START_COMMAND = "java -jar ../../frontend/target/frontend.jar ";
    private static final int CLIENT_START_DELAY_SEC = 3;

    private final String socketServerHost;
    private final int socketServerPort;
    private List<ProcessRunner> processes;

    public LaunchService(String socketServerHost, int socketServerPort){
        this.socketServerHost = socketServerHost;
        this.socketServerPort = socketServerPort;
        processes = new ArrayList<>(PROCESSES_COUNT);
        initProcesses();
    }

    public void start() throws InterruptedException, IOException {
        startProcesses();
    }

    public void shutdown(){
        stopProcesses();
    }

    private void initProcesses(){
        for(int i = 0; i < PROCESSES_COUNT; i ++){
            processes.add(new ProcessRunnerImpl());
        }
    }

    private void startProcesses() throws IOException, InterruptedException {
        Iterator<ProcessRunner> iterator = processes.iterator();
        int currentClientPort = socketServerPort + 1;
        iterator.next().start(MS_SOCKET_SERVER_START_COMMAND + socketServerPort + " -port " + currentClientPort++);

        TimeUnit.SECONDS.sleep(CLIENT_START_DELAY_SEC);

        iterator.next().start(BACKEND_FIRST_START_COMMAND + socketServerHost + " " + socketServerPort + " -port " + currentClientPort++);
        iterator.next().start(BACKEND_SECOND_START_COMMAND + socketServerHost + " "  + socketServerPort + " -port " + currentClientPort++);
        iterator.next().start(FRONTEND_FIRST_START_COMMAND + socketServerHost + " " + socketServerPort + " -port " + currentClientPort++);
        iterator.next().start(FRONTEND_SECOND_START_COMMAND + socketServerHost + " " + socketServerPort + " -port " + currentClientPort);
    }

    private void stopProcesses(){
        for(ProcessRunner processRunner: processes){
            processRunner.stop();
        }
    }
}
