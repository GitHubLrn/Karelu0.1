package com.kmust.Karelu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.kmust.Karelu.mapper")
@EnableScheduling
public class KareluServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KareluServerApplication.class,args);
    }
}
