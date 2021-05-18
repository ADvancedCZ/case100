package com.zc.case03.threadpool1;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("threadpool")
public class TestController {

    @GetMapping("right")
    public int right(){
        //track completed task number
        AtomicInteger atomicInteger = new AtomicInteger(0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                5,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10){
                    @Override
                    public boolean offer(Runnable runnable) {
                        return false;
                    }
                },
                new ThreadFactoryBuilder().setNameFormat("case03-threadpool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.prestartAllCoreThreads();
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        printStats(threadPoolExecutor);
        //submit every 1s,20 times in total
        IntStream.rangeClosed(1, 20).forEach(i ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error("interrupted when being about to submit task", e);
            }
            int number = atomicInteger.incrementAndGet();
            try {
                threadPoolExecutor.submit(()->{
                    log.info("task {} start", number);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        log.error("interrupted when execute task", e);
                    }
                    log.info("task {} finish", number);
                });
            } catch (Exception e) {
                //decrease completed task number when task failed
                log.error("met error when submitting task {}", number, e);
                atomicInteger.decrementAndGet();
            }
        });
        return atomicInteger.get();
    }

    private void printStats(ThreadPoolExecutor threadPoolExecutor) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                ()->{
                    log.info("-----------------------------------------------");
                    log.info("Pool Size (Current Number of Threads) {}",threadPoolExecutor.getPoolSize());
                    log.info("Active Count {}",threadPoolExecutor.getActiveCount());
                    log.info("Completed Task Count {}",threadPoolExecutor.getCompletedTaskCount());
                    log.info("Number of Threads in Queue {}",threadPoolExecutor.getQueue().size());
                    log.info("-----------------------------------------------");
                },
                0,1,TimeUnit.SECONDS);
    }
}
