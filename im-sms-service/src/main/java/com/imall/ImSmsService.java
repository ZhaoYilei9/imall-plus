package com.imall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ImSmsService {
    public static void main(String[] args) {
        SpringApplication.run(ImSmsService.class,args);
    }
}
