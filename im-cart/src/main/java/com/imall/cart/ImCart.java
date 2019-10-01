package com.imall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ImCart {
    public static void main(String[] args) {
        SpringApplication.run(ImCart.class,args);
    }
}
