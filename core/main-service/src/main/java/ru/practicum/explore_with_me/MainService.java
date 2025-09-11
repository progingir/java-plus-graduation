package ru.practicum.explore_with_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"ru.practicum.client"})
@SpringBootApplication
public class MainService {
    public static void main(String[] args) {
        SpringApplication.run(MainService.class, args);
    }
}
