package com.zc.case01.copyonwrite;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("copyonwrite")
public class CopyOnWriteController {

    //test write performance between copyonwrite arraylist and synchronized arraylist
    @GetMapping("write")
    public Map<String,String> testWrite(){
        HashMap<String, String> map = new HashMap<>();
        CopyOnWriteArrayList<Integer> cowArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synArrayList = Collections.synchronizedList(new ArrayList<>());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("write:CopyOnWriteArrayList");
        IntStream.rangeClosed(1, 100000).parallel().forEach(cowArrayList::add);
        stopWatch.stop();
        stopWatch.start("write:SynchronizedArrayList");
        IntStream.rangeClosed(1, 100000).parallel().forEach(synArrayList::add);
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        map.put("CopyOnWriteArrayList","size:"+cowArrayList.size());
        map.put("SynchronizedArrayList","size:"+synArrayList.size());
        return map;
    }

    //test read performance between copyonwrite arraylist and synchronized arraylist
    @GetMapping("read")
    public Map<String,String> testRead(){
        int loop = 100000;
        HashMap<String, String> map = new HashMap<>();
        CopyOnWriteArrayList<Integer> cowArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synArrayList = Collections.synchronizedList(new ArrayList<>());
        IntStream.rangeClosed(1, loop).parallel().forEach(__ -> cowArrayList.add(ThreadLocalRandom.current().nextInt(loop)));
        IntStream.rangeClosed(1, loop).parallel().forEach(__ -> synArrayList.add(ThreadLocalRandom.current().nextInt(loop)));
        int size0 = cowArrayList.size();
        int size1 = synArrayList.size();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("read:CopyOnWriteArrayList");
        IntStream.rangeClosed(1, loop).parallel().forEach(__ -> cowArrayList.get(ThreadLocalRandom.current().nextInt(size0)));
        stopWatch.stop();
        stopWatch.start("read:SynchronizedArrayList");
        IntStream.rangeClosed(1, loop).parallel().forEach(__ -> synArrayList.get(ThreadLocalRandom.current().nextInt(size1)));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        map.put("CopyOnWriteArrayList","size:"+size0);
        map.put("SynchronizedArrayList","size:"+size1);
        return map;
    }
}
