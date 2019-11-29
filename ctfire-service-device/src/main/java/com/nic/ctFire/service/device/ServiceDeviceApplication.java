package com.nic.ctFire.service.device;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.nic.ctFire")
@MapperScan(value = "com.nic.ctFire.common.service.mapper")
public class ServiceDeviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDeviceApplication.class,args);
    }
}
