package com.order_amqp.rabbitmqorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private static final String TEST_INIT_KEY = "test_init_key";
    @Autowired
    private RedisServiceImpl redisServiceImpl;

    public int test(){
        int count = (int) redisServiceImpl.get(TEST_INIT_KEY);
        return count;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        boolean b = redisServiceImpl.setNX(TEST_INIT_KEY, 100);
        logger.info("库存初始化成功,b={}", b);
    }
}
