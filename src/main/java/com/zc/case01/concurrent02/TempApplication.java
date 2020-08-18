package com.zc.case01.concurrent02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author ADvancedCZ
 * @CreateTime 2020/8/18 20:52
 * @Description
 */

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class TempApplication {
    public static void main(String[] args) {
        SpringApplication.run(TempApplication.class,args);
    }
}
