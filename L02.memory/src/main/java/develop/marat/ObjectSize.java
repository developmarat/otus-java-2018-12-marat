package develop.marat;

public class ObjectSize {
    private static int SIZE = 100000;//100 thousands

    private ObjectFactory factory;

    public ObjectSize(ObjectFactory factory) {
        this.factory = factory;
    }

    public long calculateAndGetSize() throws InterruptedException{
        Object[] objects = new Object[SIZE];

        long usedMemoryBeforeCreateObjects = getUsedMemory();

        for (int i = 0; i < objects.length; i++) {
            objects[i] = factory.create();
        }
        long usedMemoryAfterCreateObjects = getUsedMemory();
        return (usedMemoryAfterCreateObjects - usedMemoryBeforeCreateObjects) / objects.length;
    }

    private long getUsedMemory() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
