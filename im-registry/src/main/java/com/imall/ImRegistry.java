package com.imall;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class ImRegistry {
	public static void main(String[] args) {
		SpringApplication.run(ImRegistry.class, args);
		log.info("***registry启动成功***");
	}
}
