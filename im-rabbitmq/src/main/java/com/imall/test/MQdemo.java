//package com.imall.test;
//
//
//import com.imall.ImRabbitMQ;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ImRabbitMQ.class)
//public class MQdemo {
//     @Autowired
//    private AmqpTemplate amqpTemplate;
//
//    @Test
//    public void testSend() throws InterruptedException {
//        String msg = "hello, Spring boot amqp";
//        this.amqpTemplate.convertAndSend("fanout_exchange_direct_test","a.b", msg);
//        // 等待10秒后再结束
////        Thread.sleep(10000);
//    }
//}
