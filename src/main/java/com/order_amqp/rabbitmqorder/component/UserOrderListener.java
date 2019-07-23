package com.order_amqp.rabbitmqorder.component;

import com.order_amqp.rabbitmqorder.service.UserOrderService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *  配置用户订单监听类,确认消费处理逻辑
 */
@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderListener.class);

    @Autowired
    private UserOrderService userOrderService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long tag = message.getMessageProperties().getDeliveryTag();
        try {
            String phone = new String(message.getBody());
//            logger.info("监听到抢单手机号phone:{}", phone);
            userOrderService.manageRobbing(phone);
            // 确认消费
            // TODO 参数的意思
            channel.basicAck(tag, true);
        } catch (IOException e) {
            logger.error("用户抢单失败|发生异常:{}",e.fillInStackTrace());
            // TODO 参数的意思
            channel.basicReject(tag, false);
        }
    }
}
