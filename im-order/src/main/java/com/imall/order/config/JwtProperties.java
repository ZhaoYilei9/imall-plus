package com.imall.order.config;

import com.imall.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "im.jwt")
@Data
@Slf4j
public class JwtProperties {


    private String pubKeyPath;
    private String cookieName;
    private PublicKey publicKey; // 公钥


    @PostConstruct
    public void init(){
        try {
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        }catch (Exception e){
            log.error("初始化公钥私钥失败,", e);
            throw new RuntimeException();
        }
    }
}
