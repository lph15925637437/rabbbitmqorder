package com.order_amqp.rabbitmqorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 *  模拟用户高并发时的多线程请求
 */
@Service
public class InitService {

    private static final Logger logger = LoggerFactory.getLogger(InitService.class);

    @Value("${reuqest.num}")
    private int requestNum;

    // 随机的手机号码
    public static int phone  = 0;

    @Autowired
    private SenderMqService senderMqService;

    public void generateMultiThread(){
        logger.info("开始初始化线程数-----> ");
        try {
            CountDownLatch latch = new CountDownLatch(1);
            for (int i = 0; i < requestNum; i++) {
                new Thread(new RunThread(latch)).start();
            }
            // 线程启动
            latch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class RunThread implements Runnable{

        private final CountDownLatch latch;

        public RunThread(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                // 线程等待
                latch.await();
                phone += 1;
                // TODO 发送消息入抢单队列
                senderMqService.sendRobbingMsg(String.valueOf(phone));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
