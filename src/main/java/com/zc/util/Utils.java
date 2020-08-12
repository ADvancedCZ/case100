package com.zc.util;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

@Slf4j
public class Utils {
    public static void loadPropertySource(Class clazz,String fileName) {
        Properties p = new Properties();
        try {
            p.load(clazz.getResourceAsStream(fileName));
            p.forEach((k,v)->{
            log.info("{}={}",k,v);
                    System.setProperty(k.toString(),v.toString());
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
