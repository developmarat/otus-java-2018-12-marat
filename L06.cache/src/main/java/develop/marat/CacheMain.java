package develop.marat;

/**
 * VM options: -Xmx256m -Xms256m
 */
public class CacheMain {
    public static void main(String... args) throws InterruptedException {
        //new CacheMain().eternalCacheExample();
        //new CacheMain().lifeCacheExample();
        new CacheMain().linkLifeCacheExample();
    }

    private void eternalCacheExample() {
        final int size = 7;
        CacheEngine<Integer, String> cache = new MyCacheEngine<>(size, 0, 0, true);

        for (int i = 0; i < 10; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < 10; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void lifeCacheExample() throws InterruptedException {
        final int size = 5;
        CacheEngine<Integer, String> cache = new MyCacheEngine<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, "String: " + i));
        }

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            MyElement<Integer, String> element = cache.get(i);
            System.out.println("String for " + i + ": " + (element != null ? element.getValue() : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void linkLifeCacheExample() {
        final int size = 10;
        CacheEngine<Integer, BigObject> cache = new MyCacheEngine<>(size, 0, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(new MyElement<>(i, new BigObject()));
        }

        System.gc();

        int countExistsValue = 0;
        for (int i = 0; i < size; i++) {
            MyElement<Integer, BigObject> element = cache.get(i);
            if(element != null){
                countExistsValue++;
            }
        }

        System.out.println("Count exists elements: " + countExistsValue);
    }

    static class BigObject {
        final byte[] array = new byte[1024 * 1024 * 100];

        public byte[] getArray() {
            return array;
        }
    }
}
