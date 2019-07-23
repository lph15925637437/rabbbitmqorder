package com.order_amqp.rabbitmqorder.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 处理抢单业务类
 */
@Service
public class UserOrderService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderService.class);

    // 假定10010对应的商品为抢单活动商品
    private static final String ProductNo = "10010";

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    @Value("${user.order.init.key}")
    private String user_order_init_key;

    /**
     * 处理抢单
     */
    // TODO 下面可以通过redis进行库存的控制
    public void manageRobbing(String phone) {
        try {
            // 进行库存的判断以及库存足够时进行用户信息的记录
            long decr = redisServiceImpl.decrBy(user_order_init_key, 1);
            logger.info("剩余库存数re_stock={}",decr);
            if (decr >= 0) {
                logger.info("抢单成功phone={}",phone);
                // 进行抢单用户信息的记录...
                String path = "F:\\robbing_user_record\\success_record.txt";
                writeMsg2File("抢单成功的手机号码: ", path, phone);
            } else {
                // 记录抢单失败用户信息
                logger.info("库存不足，无法进行正常抢单活动,下次继续参与吧!!");
                String path = "F:\\robbing_user_record\\fail_record.txt";
                writeMsg2File("抢单失败的手机号码: ", path, phone);
            }
        } catch (Exception e) {
            logger.error("处理抢单失败:phone={}", phone);
        }
    }

    /**
     *  将信息写入到文件中
     */
    private void writeMsg2File(String name, String filePath, String phone){
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.append(name);
            bw.write(phone+"\r\n ");// 往已有的文件上添加字符串
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化商品库存数量缓存到redis中
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        boolean robbingNo = redisServiceImpl.setNX(user_order_init_key, 10);
        logger.info("商品库存初始化成功,robbing={}", robbingNo);
    }
}
