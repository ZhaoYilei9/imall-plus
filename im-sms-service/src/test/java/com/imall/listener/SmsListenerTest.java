package com.imall.listener;

import com.imall.ImSmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImSmsService.class)
public class SmsListenerTest {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Test
    public void listenVerifyCode() throws InterruptedException {
        Map<String,String> map = new HashMap<>();
        map.put("phone", "15318083976");
        map.put("code", "123456");
        amqpTemplate.convertAndSend("im.sms.exchange", "sms.verify.code", map);

        Thread.sleep(5000);
    }
}