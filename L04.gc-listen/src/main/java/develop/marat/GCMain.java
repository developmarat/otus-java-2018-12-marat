package develop.marat;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class GCMain {
    private final static int NUMBER_OF_STRINGS = 15000;
    private final static int PAUSE_IN_MILLISECONDS = 1000;

    public static void main(String[] args) throws InterruptedException {
        initGCNotificationListener();
        long startTime= System.currentTimeMillis();

        System.out.println(ManagementFactory.getRuntimeMXBean().getVmName());
        System.out.println(ManagementFactory.getRuntimeMXBean().getInputArguments());
        System.out.println();

        ArrayList<String> list = new ArrayList<>();
        int lastPrintTime = 0;
        int halfNumberOfStrings = NUMBER_OF_STRINGS/2;

        while (true) {
            for (int i = 0; i < NUMBER_OF_STRINGS; i++) {
                list.add(new String());
            }

            for (int i = 0; i < halfNumberOfStrings; i++) {
                list.remove(list.size() - 1);
            }
            Thread.sleep(PAUSE_IN_MILLISECONDS);

            int passedMinutes = convertMillisecondsToMinutes(System.currentTimeMillis() - startTime);
            if(passedMinutes > lastPrintTime){
                System.out.println("\nPassed " + passedMinutes + " minutes");
                GCNotificationListener.getInstance().getStatistics().print();
                lastPrintTime = passedMinutes;
            }
        }
    }

    private static void initGCNotificationListener() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            emitter.addNotificationListener(GCNotificationListener.getInstance(), null, null);
        }
    }

    private static int convertMillisecondsToMinutes(long milliseconds){
        return (int)milliseconds / (1000 * 60);
    }
}
