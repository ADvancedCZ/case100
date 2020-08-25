package com.zc.case01.copyonwrite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class CommonMistakesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class,args);
    }
}
