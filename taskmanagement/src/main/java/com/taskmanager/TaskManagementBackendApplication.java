package com.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TaskManagementBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagementBackendApplication.class, args);
    }
}
