package com.imall.listener;

import com.imall.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GoodsListener {
    @Autowired
    private IndexService indexService;
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "im.search.insert.queue", durable = "true"),
            exchange = @Exchange(name = "im.item.exchange",
                    type = ExchangeTypes.TOPIC,
                    ignoreDeclarationExceptions = "true"),
            key = {"item.insert", "item.update"}
            ))
    public void listenInsert(Long id) {
        //监听新增或更新
        if (id != null) {
            indexService.insertOrUpdate(id);
//            log.info("***消息--id:{}",id);
        }

    }

}
