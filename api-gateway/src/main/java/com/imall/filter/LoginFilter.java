package com.imall.filter;

import com.imall.auth.entity.UserInfo;
import com.imall.auth.utils.JwtUtils;
import com.imall.common.utils.CookieUtils;
import com.imall.properties.FilterProperties;
import com.imall.properties.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class,FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    public static final String PRE_TYPE = "pre";
    public static final int PRE_DECORATION_FILTER_ORDER = 5;

    @Autowired
    private FilterProperties filterProps;


    @Autowired
    private JwtProperties props;
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String requestURI = request.getRequestURI();

        return !isAllowPath(requestURI);
    }

    private boolean isAllowPath(String requestURI) {
        boolean flag = false;
        List<String> allowPaths = filterProps.getAllowPaths();
        for (String allowPath : allowPaths) {
            if (requestURI.startsWith(allowPath)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String token = CookieUtils.getCookieValue(request, props.getCookieName());
        try {
            UserInfo userInfo = JwtUtils.getUserInfo(props.getPublicKey(), token);
        } catch (Exception e){
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(403);
        }
        return null;
    }
}
