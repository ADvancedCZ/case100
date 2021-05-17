package com.zc.case03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Case03_Application {
    public static void main(String[] args) {
        SpringApplication.run(Case03_Application.class, args);
    }
}
