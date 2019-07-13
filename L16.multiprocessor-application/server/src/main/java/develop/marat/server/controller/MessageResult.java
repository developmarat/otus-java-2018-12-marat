package develop.marat.server.controller;


import develop.marat.ms.annotation.Blocks;

import java.util.UUID;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageResult {
    private final UUID messageId;
    private volatile Object result = null;
    private volatile boolean resultIsReady = false;

    private final Lock lock = new ReentrantLock();
    private final Condition resultIsReadyCondition = lock.newCondition();

    public MessageResult(UUID messageId){
        this.messageId = messageId;
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

    @Blocks
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
