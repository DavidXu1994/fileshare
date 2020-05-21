package com.bysj.fileshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class FileshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileshareApplication.class, args);
    }

}
