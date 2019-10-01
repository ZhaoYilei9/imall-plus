package com.imall.auth.properties;

import com.imall.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

@ConfigurationProperties(prefix = "im.jwt")
@Data
@Slf4j
public class JwtProperties {

    private String secret;
    private String pubKeyPath;
    private String priKeyPath;
    private Integer expire;
    private String cookieName;
    private Integer cookieMaxAge;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init(){
        try {
            File pubKeyFile = new File(pubKeyPath);
            File priKeyFile = new File(priKeyPath);
            if (!priKeyFile.exists() || !pubKeyFile.exists()){
                RsaUtils.generateKey(pubKeyPath,priKeyPath,secret);
            }
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);

        }catch (Exception e){
            log.error("初始化公钥私钥失败,", e);
            throw new RuntimeException();
        }
    }
}
