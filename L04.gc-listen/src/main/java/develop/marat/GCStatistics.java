package develop.marat;

public class GCStatistics {

    private int youngGenQuantity;
    private int oldGenQuantity;

    private long youngGenTotalDuration;
    private long oldGenTotalDuration;

    public int getYoungGenQuantity() {
        return youngGenQuantity;
    }

    public void setYoungGenQuantity(int youngGenQuantity) {
        this.youngGenQuantity = youngGenQuantity;
    }

    public int getOldGenQuantity() {
        return oldGenQuantity;
    }

    public void setOldGenQuantity(int oldGenQuantity) {
        this.oldGenQuantity = oldGenQuantity;
    }

    public long getYoungGenTotalDuration() {
        return youngGenTotalDuration;
    }

    public void setYoungGenTotalDuration(long youngGenTotalDuration) {
        this.youngGenTotalDuration = youngGenTotalDuration;
    }

    public long getOldGenTotalDuration() {
        return oldGenTotalDuration;
    }

    public void setOldGenTotalDuration(long oldGenTotalDuration) {
        this.oldGenTotalDuration = oldGenTotalDuration;
    }

    public void clean() {
        this.youngGenQuantity = 0;
        this.oldGenQuantity = 0;
        this.youngGenTotalDuration = 0;
        this.oldGenTotalDuration = 0;
    }

    @Override
    public String toString() {
        return "\nGCStatistics{" +
                "\nyoungGenQuantity=" + youngGenQuantity +
                ",\n youngGenTotalDuration=" + youngGenTotalDuration +
                ",\n oldGenQuantity=" + oldGenQuantity +
                ",\n oldGenTotalDuration=" + oldGenTotalDuration +
                "\n}";
    }

    public void print() {
        System.out.println(this.toString());
    }
}
