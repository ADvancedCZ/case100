package com.zc.case01.concurrent01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Slf4j
@RestController
@RequestMapping("concurrent")
public class ConcurrentHashMapMisuseController {
    private static final int THREAD_COUNT = 10;
    private static final int ITEM_COUNT = 1000;


    @RequestMapping("wrong1")
    public String wrong1() throws InterruptedException {
        ConcurrentHashMap<String, Long> map = getData(ITEM_COUNT - 100);
        //print log to show map size before add data
        log.info("before:map size:{}", map.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        for (int i = 0; i < forkJoinPool.getParallelism(); i++) {
            forkJoinPool.execute(() -> {
                int gap = ITEM_COUNT - map.size();
                log.info("gap size:{}", gap);
                map.putAll(getData(gap));
            });
        }
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("after:map size:{}", map.size());
        return "OK";
    }

    @RequestMapping("right1")
    public String right2() throws InterruptedException {
        ConcurrentHashMap<String, Long> map = getData(ITEM_COUNT - 100);
        //print log to show map size before add data
        log.info("before:map size:{}", map.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        for (int i = 0; i < forkJoinPool.getParallelism(); i++) {
            forkJoinPool.execute(() -> {
                synchronized (map) {
                    int gap = ITEM_COUNT - map.size();
                    log.info("gap size:{}", gap);
                    map.putAll(getData(gap));
                }
            });
        }
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("after:map size:{}", map.size());
        return "OK";
    }

    @RequestMapping("wrong2")
    public String wrong2() throws InterruptedException {
        ConcurrentHashMap<String, Long> map = getData(ITEM_COUNT - 100);
        //print log to show map size before add data
        log.info("before:map size:{}", map.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        for (int i = 0; i < forkJoinPool.getParallelism(); i++) {
            forkJoinPool.execute(() -> {
                int gap = ITEM_COUNT - map.size();
                log.info("gap size:{}", gap);
                map.putAll(getData(gap));
            });
        }
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("after:map size:{}", map.size());
        return "OK";
    }

    @RequestMapping("right2")
    public String right1() throws InterruptedException {
        ConcurrentHashMap<String, Long> map = getData(ITEM_COUNT - 100);
        //print log to show map size before add data
        log.info("before:map size:{}", map.size());
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() ->
                IntStream.rangeClosed(1,10).parallel().forEach(
                        i -> {
                            synchronized (map) {
                                int gap = ITEM_COUNT - map.size();
                                log.info("gap size:{}", gap);
                                map.putAll(getData(gap));
                            }
                        }));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("after:map size:{}", map.size());
        return "OK";
    }

    /**
     * simulate to get a ConcurrentHashMap that has certain number data
     * @param count number
     * @return ConcurrentHashMap
     */
    private ConcurrentHashMap<String, Long> getData(int count) {
        //pretend to have 900 data already here
//        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        /*for (long i = 0; i < count; i++) {
            map.put(UUID.randomUUID().toString(), i);
        }*/
//        LongStream.rangeClosed(1, count).forEach(i ->map.put(UUID.randomUUID().toString(),i));
        return LongStream.
                rangeClosed(1, count)
                .boxed()
                .parallel()
                .collect(Collectors.toConcurrentMap(t -> UUID.randomUUID().toString(), Function.identity(), (o1, o2) -> o1, ConcurrentHashMap::new));
    }
}

