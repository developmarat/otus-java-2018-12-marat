package develop.marat;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.time.LocalDateTime;

public class GCNotificationListener implements NotificationListener {

    private GCStatistics statistics;

    private static GCNotificationListener instance = null;

    private GCNotificationListener() {
        statistics = new GCStatistics();
    }

    public static GCNotificationListener getInstance(){
        if(instance == null){
            instance = new GCNotificationListener();
        }

        return instance;
    }

    public GCStatistics getStatistics() {
        return this.statistics;
    }

    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            String gcType = info.getGcAction();

            if ("end of minor GC".equals(gcType)) {
                gcType = "Young Gen GC";
                this.statistics.setYoungGenQuantity(this.statistics.getYoungGenQuantity() + 1);
                this.statistics.setYoungGenTotalDuration(this.statistics.getYoungGenTotalDuration() + info.getGcInfo().getDuration());
            } else if ("end of major GC".equals(gcType)) {
                gcType = "Old Gen GC";
                this.statistics.setOldGenQuantity(this.statistics.getOldGenQuantity() + 1);
                this.statistics.setOldGenTotalDuration(this.statistics.getOldGenTotalDuration() + info.getGcInfo().getDuration());
            }

            print(info, gcType);
        }
    }

    private void print(GarbageCollectionNotificationInfo info, String gcType){
        System.out.println();
        System.out.println("[" + LocalDateTime.now() + "] " +
                gcType + ": - " +
                info.getGcInfo().getId()+ " " +
                info.getGcName() + " (from " +
                info.getGcCause()+") " + info.getGcInfo().getDuration() + " milliseconds; start-end times " +
                info.getGcInfo().getStartTime()+ "-" +
                info.getGcInfo().getEndTime());
    }
}
