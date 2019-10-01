package com.imall.auth.controller;

import com.imall.auth.entity.UserInfo;
import com.imall.auth.properties.JwtProperties;
import com.imall.auth.service.AuthService;
import com.imall.auth.utils.JwtUtils;
import com.imall.common.utils.CookieUtils;
import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties props;

    @PostMapping("accredit")
    public ResponseEntity<Void> login(@RequestParam("username") String usernmae,
                                      @RequestParam("password") String password,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        final String token = authService.login(usernmae, password);
        if (StringUtils.isBlank(token)) {
            throw new ImException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN") String token,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            //从token中获取用户信息
            UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
            String newToken = JwtUtils.generateToken(userInfo, props.getPrivateKey(), props.getExpire());
            CookieUtils.newBuilder(response).httpOnly().maxAge(props.getCookieMaxAge()).request(request).build(props.getCookieName(), newToken);
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
