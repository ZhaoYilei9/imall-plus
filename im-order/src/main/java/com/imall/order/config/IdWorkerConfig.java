package com.imall.order.config;

import com.imall.common.utils.IdWorker;
import com.imall.order.properties.IdWorkerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdWorkerProperties.class)
public class IdWorkerConfig {

    @Autowired
    private IdWorkerProperties props ;

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(props.getWorkerId(),props.getDataCenterId());
    }
}
