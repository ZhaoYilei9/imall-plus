package com.imall.order.filter;

import com.imall.auth.entity.UserInfo;
import com.imall.auth.utils.JwtUtils;
import com.imall.common.utils.CookieUtils;
import com.imall.order.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableConfigurationProperties(JwtProperties.class)
@Slf4j
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtProperties props ;

    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public UserInterceptor(JwtProperties props) {
        this.props = props;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = CookieUtils.getCookieValue(request, props.getCookieName());
        if (StringUtils.isBlank(token)){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        try {
            UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
            tl.set(userInfo);

            return true;
        }catch (Exception e){
            log.error("*****[购物车服务]-[解析用户信息]出错",e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        tl.remove();
    }

    public static UserInfo getLoginUser(){

        return tl.get();
    }


}
