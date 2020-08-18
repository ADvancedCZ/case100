package com.zc.case01.threadlocal;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("threadLocal")
public class ThreadLocalMisuseController {
    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(()->null);
//    private static final ThreadLocal<Integer> currentUser = new ThreadLocal<>();

    @GetMapping("wrong")
    public Map<String,String> wrong(@RequestParam("userId") Integer userId){
        return getMap(userId);
    }

    @GetMapping("right")
    public Map<String,String> right(@RequestParam("userId") Integer userId){
        try {
            return getMap(userId);
        } finally {
            currentUser.remove();
        }
    }

    private Map<String, String> getMap(Integer userId) {
        String before = Thread.currentThread().getName() + ":" + currentUser.get();
        currentUser.set(userId);
        String after = Thread.currentThread().getName() + ":" + currentUser.get();
        HashMap<String, String> map = new HashMap<>();
        map.put("before", before);
        map.put("after", after);
        return map;
    }

}
