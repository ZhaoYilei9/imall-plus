package com.imall.exchange.fanout;

import com.imall.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * Fanout Exchange
 * 在广播模式下，消息发送流程是这样的：
    1）  可以有多个消费者
    2）  每个消费者有自己的queue（队列）
    3）  每个队列都要绑定到Exchange（交换机）
    4）  生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定。
    5）  交换机把消息发送给绑定过的所有队列
    6）  队列的消费者都能拿到消息。实现一条消息被多个消费者消费
 */
public class FanoutSend {
    private final static String EXCHANGE_NAME = "fanout_exchange_test_2";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();

        // 声明exchange，指定类型为fanout,持久化为true
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout",true);

        // 消息内容
        String message = "Hello everyone";
        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println(" [生产者] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
