package com.order_amqp.rabbitmqorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 将数据发送至rabbitmq中
 */
@Service
public class InitService2UserOrder {

    private static final Logger logger = LoggerFactory.getLogger(InitService2UserOrder.class);

    @Autowired
    private SenderMqService senderMqService;

    // TODO 可以通过多线程的异步方式进行优化
    @Async("sentinelSimpleAsync")
    public void initConcurrentNum(String phone) {
        senderMqService.sendRobbingMsg(String.valueOf(phone));
    }


}
