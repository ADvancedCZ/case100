package com.zc.case03.threadpool1;

import java.util.concurrent.ArrayBlockingQueue;

public class ExtremeArrayBlockingQueue<E> extends ArrayBlockingQueue<E> {

    public ExtremeArrayBlockingQueue(int capacity) {
        super(capacity);
    }


    /**
     * when thread number is not less than corePoolSize,create new thread rather than add task into queue
     */
    @Override
    public boolean offer(E e) {
        return false;
    }

    /**
     * invoke super offer method
     * @param e task
     * @return false: the queue is full,vice versa
     */
    public boolean extremeOffer(E e){
        return super.offer(e);
    }
}
