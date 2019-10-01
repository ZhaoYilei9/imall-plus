package com.imall.user.web;

import com.imall.user.pojo.User;
import com.imall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验用户名和手机号码是否被占用
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> check(@PathVariable("data") String data,
                                @PathVariable("type") Integer type){
        Boolean isOk = userService.check(data, type);
        return ResponseEntity.ok(isOk);
    }

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @PostMapping("send")
    public ResponseEntity<Void> sendMessage(@RequestParam("phone") String phone){
        log.info("*****[用户服务]-[发送信息]-手机号:{}",phone);
        userService.sendVerifyCode(phone);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code){
        userService.register(user,code);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.queryUser(username, password);

        return ResponseEntity.ok(user);
    }

}
