package develop.marat.ms.messageSystem;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageResult {
    private final Message message;
    private volatile Object result = null;
    private volatile boolean resultIsReady = false;

    private final Lock lock = new ReentrantLock();
    private final Condition resultIsReadyCondition = lock.newCondition();

    public MessageResult(Message message){
        this.message = message;
    }

    public void setResult(Object result){
        this.result = result;
        this.resultIsReady = true;

        lock.lock();
        try{
            resultIsReadyCondition.signal();
        }finally{
            lock.unlock();
        }

    }

    @SuppressWarnings("unchecked")
    public <T> T takeResult(){
        lock.lock();
        try{
            while (!resultIsReady) {
                resultIsReadyCondition.await();
            }
            resultIsReadyCondition.signal();
            return (T)result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }

}
