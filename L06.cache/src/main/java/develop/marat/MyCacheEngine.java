package develop.marat;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MyCacheEngine<K, V> implements CacheEngine<K, V> {
    private static final int TIME_THRESHOLD_MS = 10;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<MyElement<K, V>> > elements = new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;

    MyCacheEngine(int maxElements, long lifeTimeMs, long idleTimeMs, boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    public void put(MyElement<K, V> element) {
        checkSize();

        final K key = element.getKey();
        SoftReference<MyElement<K, V>> elementReference = new SoftReference<>(element);
        elements.put(key, elementReference);

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                createCheckLifeTimeTask(key);
            }
            if (idleTimeMs != 0) {
                createCheckIdleTimeTask(key);
            }
        }
    }

    public MyElement<K, V> get(K key) {
        MyElement<K, V> element = getElement(key);
        if (element != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }

        return element;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }

    public void dispose() {
        timer.cancel();
    }

    private void checkSize() {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            removeElement(firstKey);
        }
    }

    private void createCheckLifeTimeTask(final K key) {
        TimerTask lifeTimerTask = new TimerTask() {
            @Override
            public void run() {
                removeElement(key);
                this.cancel();
            }
        };
        timer.schedule(lifeTimerTask, lifeTimeMs);
    }

    private void createCheckIdleTimeTask(final K key) {
        TimerTask idleTimerTask = new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> element = getElement(key);
                if (element == null || isT1BeforeT2((element.getLastAccessTime() + idleTimeMs), System.currentTimeMillis())) {
                    removeElement(key);
                    this.cancel();
                }
            }
        };
        timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
    }

    private MyElement<K, V> getElement(K key) {
        SoftReference<MyElement<K, V>> elementReference = elements.get(key);
        if(elementReference != null){
            MyElement<K, V> element = elementReference.get();
            if(element != null){
                return element;
            }
            else{
                removeElement(key);
                return null;
            }
        }
        else{
            return null;
        }
    }

    private void removeElement(K key) {
        SoftReference<MyElement<K, V>> elementReference = elements.get(key);
        if(elementReference != null){
            elementReference.clear();
            elements.remove(key);
        }
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }
}
