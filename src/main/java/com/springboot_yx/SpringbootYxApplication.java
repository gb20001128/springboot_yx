package com.springboot_yx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling //开启定时任务
public class SpringbootYxApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootYxApplication.class, args);
    }

}
