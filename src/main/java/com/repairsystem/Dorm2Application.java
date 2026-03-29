package com.repairsystem;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.repairsystem.mapper")
public class Dorm2Application {

    public static void main(String[] args) {
        SpringApplication.run(Dorm2Application.class, args);
    }
}
