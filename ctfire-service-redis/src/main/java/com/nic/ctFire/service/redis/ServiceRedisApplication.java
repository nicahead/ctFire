package com.nic.ctFire.service.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceRedisApplication.class,args);
    }
}
