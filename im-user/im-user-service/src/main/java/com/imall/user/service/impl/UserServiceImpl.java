package com.imall.user.service.impl;

import com.imall.common.utils.NumberUtils;
import com.imall.enums.ExceptionEnum;
import com.imall.exception.ImException;
import com.imall.user.mapper.UserMapper;
import com.imall.user.pojo.User;
import com.imall.user.service.UserService;
import com.imall.user.utils.CodeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:code:";

    @Override
    public Boolean check(String data, Integer type) {
        Boolean isOk = false;
        User record = new User();
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
        }
        int i = userMapper.selectCount(record);
        if (i == 0) {
            isOk = true;
        }
        return isOk;
    }

    @Override
    public void sendVerifyCode(String phone) {
        //1.生成验证码
        String code = NumberUtils.generateCode(6);
        //2.生成redis存储的前缀
        String key = KEY_PREFIX + phone;
        //3.将数据存到redis中
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);
        //4.向mq发送消息
        //向mq中发送消息
        Map<String,String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", map);
    }

    @Override
    public void register(User user, String code) {

        String key = KEY_PREFIX + user.getPhone();
        String codeInRedis = (String)redisTemplate.opsForValue().get(key);
        if (!StringUtils.equals(code,codeInRedis)){
            throw new ImException(ExceptionEnum.VERIFY_CODE_NOT_MATCHING);
        }
        user.setCreated(new Date());
        user.setSalt(CodeUtils.generateSalt());
        user.setPassword(CodeUtils.md5Hex(user.getPassword(),user.getSalt()));
        int insert = userMapper.insert(user);
        if (insert != 1){
            throw new ImException(ExceptionEnum.INVALID_PARAM);
        }

    }

    @Override
    public User queryUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        User record = userMapper.selectOne(user);
        if (record == null){
            throw  new ImException(ExceptionEnum.USER_NOT_EXIST);
        }
        if (!StringUtils.equals(record.getPassword(), CodeUtils.md5Hex(password,record.getSalt()))){
            throw new ImException(ExceptionEnum.PASSWORD_NOT_MATCHING);
        }

        return record;
    }

}
