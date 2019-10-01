package com.imall.user.service;

import com.imall.user.pojo.User;

public interface UserService {
    Boolean check(String data, Integer type);

    void sendVerifyCode(String phone);

    void register(User user, String code);

    User queryUser(String username, String password);
}
