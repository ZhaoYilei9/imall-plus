package com.imall.auth.service.impl;

import com.imall.auth.client.UserClient;
import com.imall.auth.entity.UserInfo;
import com.imall.auth.properties.JwtProperties;
import com.imall.auth.service.AuthService;
import com.imall.auth.utils.JwtUtils;
import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import com.imall.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@EnableConfigurationProperties(JwtProperties.class)
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties props ;

    @Override
    public String login(String username, String password) {
        try {
            User user = userClient.queryUser(username, password);

            if (user == null) {
                return null;
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(username);
            String token = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());

            return token;
        }catch (Exception e){
            log.error("*****[授权中心]-[登录]错误-用户名:{}", username, e);
            throw new ImException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
    }
}
