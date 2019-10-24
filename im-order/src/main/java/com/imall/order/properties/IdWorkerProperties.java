package com.imall.order.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "im.worker")
public class IdWorkerProperties {
    private long workerId;
    private long dataCenterId;
}
