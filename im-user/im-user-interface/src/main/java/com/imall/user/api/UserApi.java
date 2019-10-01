package com.imall.user.api;

import com.imall.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
* @Description TODO
  用户服务对外提供的借口
* @Return User
* @Author zyl
* @Date 2019/9/27
**/
public interface UserApi {
    @GetMapping("query")
    public User queryUser(@RequestParam("username") String username, @RequestParam("password") String password);
}
