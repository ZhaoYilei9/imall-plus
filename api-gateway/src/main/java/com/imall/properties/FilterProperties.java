package com.imall.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "im.filter")
@Data
@Slf4j
public class FilterProperties {
    
    private List<String> allowPaths;
}
