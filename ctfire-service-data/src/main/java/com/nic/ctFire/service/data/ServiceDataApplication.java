package com.nic.ctFire.service.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@MapperScan(value = "com.nic.ctFire.common.service.mapper")
public class ServiceDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDataApplication.class,args);
    }
}
