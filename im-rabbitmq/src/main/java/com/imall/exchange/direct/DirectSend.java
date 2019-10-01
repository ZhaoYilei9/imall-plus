package com.imall.exchange.direct;

import com.imall.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Direct Exchange
 * 不同的消息被不同的队列消费
 * -1 队列与交换机的绑定，不能是任意绑定了，**`而是要指定一个RoutingKey（路由key）`**
 * -2 消息的发送方在 向 Exchange发送消息时，也必须指定消息的 `RoutingKey`。
 * -3 Exchange不再把消息交给每一个绑定的队列，而是根据消息的`Routing Key`进行判断，
 * ---只有队列的`Routingkey`与消息的 `Routing key`完全一致，才会接收到消息
 */
public class DirectSend {
    private final static String EXCHANGE_NAME = "im.item.exchange";

    public static void main(String[] argv) throws Exception {
        // 获取到连接
        Connection connection = ConnectionUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();

        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String message = "商品新增了，id为1001";
        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "insert", null, message.getBytes());
        System.out.println(" [生产者] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
