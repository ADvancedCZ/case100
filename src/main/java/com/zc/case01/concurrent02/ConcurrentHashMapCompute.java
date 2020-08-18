package com.zc.case01.concurrent02;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
    @RestController
    @RequestMapping("concurrent")
public class ConcurrentHashMapCompute {
    private static final int THREAD_NUM = 10;
    private static final int KEY_NUM = 10;
    private static final int LOOP_NUM = 10000000;

    @RequestMapping("time")
    public void time(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("normal use");
        Map<String, Long> normalUseMap = normalUse();
        stopWatch.stop();
        System.out.println(normalUseMap.size());
        log.error(normalUseMap.size()+"");
        Assert.isTrue(normalUseMap.size()==KEY_NUM,"size error");
        Assert.isTrue(normalUseMap.values().stream().mapToLong(along ->along).reduce(0,Long::sum)==LOOP_NUM,"loop error");
        stopWatch.start("good use");
        Map<String, Long> goodUseMap = goodUse();
        stopWatch.stop();
        Assert.isTrue(goodUseMap.size()==KEY_NUM,"size error");
        Assert.isTrue(goodUseMap.values().stream().mapToLong(along ->along).reduce(0,Long::sum)==LOOP_NUM,"loop error");
        log.info(stopWatch.prettyPrint());
    }

    private Map<String, Long> normalUse() {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>(KEY_NUM);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_NUM);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1,LOOP_NUM).parallel().forEach(
                i -> {
                    //generate a random key
                    synchronized (map) {
                        String key = ThreadLocalRandom.current().nextInt(KEY_NUM) + "";
                        if (map.containsKey(key)) {
                            map.put(key, map.get(key) + 1);
                        } else {
                            map.put(key, 1L);
                        }
                    }
                })

        );
        return map;
    }

    private Map<String, Long> goodUse() {
        ConcurrentHashMap<String, LongAdder> map = new ConcurrentHashMap<>(KEY_NUM);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_NUM);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1,LOOP_NUM).parallel().forEach(
                i -> {
                    //generate a random key
                    String key = ThreadLocalRandom.current().nextInt(KEY_NUM) + "";
                    map.computeIfAbsent(key, k -> new LongAdder()).increment();
                })

        );
        return map.entrySet().stream().collect(
//                Collectors.toConcurrentMap(t -> t.getKey(), t -> t.getValue(), (o1, o2) -> o1),ConcurrentHashMap::new);
                Collectors.toMap(Map.Entry::getKey, t-> t.getValue().longValue())
        );
    }

}

