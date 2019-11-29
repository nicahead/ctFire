package com.nic.ctFire.web.device;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.nic.ctFire")
public class WebDeviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebDeviceApplication.class, args);
    }
}
