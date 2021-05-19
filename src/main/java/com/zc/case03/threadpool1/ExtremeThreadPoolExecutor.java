package com.zc.case03.threadpool1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExtremeThreadPoolExecutor extends ThreadPoolExecutor {

    public ExtremeThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * A handler for rejected tasks that throws a
     * {@code RejectedExecutionException}.
     *
     * when this policy triggered means the thread number is maximumPoolSize
     * then add task into queue until the queue is full
     */
    public static class EnqueueThenAbortPolicy implements RejectedExecutionHandler {
        /**
         * Creates an {@code EnqueueThenAbortPolicy}.
         */
        public EnqueueThenAbortPolicy() { }

        /**
         * Always throws RejectedExecutionException.
         *
         * @param r the runnable task requested to be executed
         * @param e the executor attempting to execute this task
         * @throws RejectedExecutionException if the queue is full
         */
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            if (!e.isShutdown()) {
                //if the queue is full,throw RejectedExecutionException
                if (!((ExtremeArrayBlockingQueue) e.getQueue()).extremeOffer(r)) {
                    throw new RejectedExecutionException("Task " + r.toString() +
                            " rejected from " +
                            e.toString());
                }
            }
        }
    }

}