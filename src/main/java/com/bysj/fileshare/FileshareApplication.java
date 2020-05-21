package com.bysj.fileshare;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


 @EntityScan(basePackages={"com.bysj.fileshare.entity"})
 @MapperScan(basePackages={"com.bysj.fileshare.mybatis.mapper"})
@SpringBootApplication
public class FileshareApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileshareApplication.class, args);
    }

}
