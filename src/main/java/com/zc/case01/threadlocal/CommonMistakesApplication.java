package com.zc.case01.threadlocal;

import com.zc.util.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CommonMistakesApplication {
    public static void main(String[] args) {
        Utils.loadPropertySource(CommonMistakesApplication.class,"tomcat.properties");
        SpringApplication.run(CommonMistakesApplication.class,args);
    }
}
