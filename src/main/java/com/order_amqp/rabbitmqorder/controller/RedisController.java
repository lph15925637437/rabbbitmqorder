package com.order_amqp.rabbitmqorder.controller;

import com.order_amqp.rabbitmqorder.service.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisServiceImpl redisServiceImpl;

    @RequestMapping("/test/redis")
    public String testRedis() {
        boolean b = redisServiceImpl.set("rabbit_redis", "15925637437");
        logger.info("将数据存放到redis返回数据,bool={}", b);

        return "success";
    }

    @RequestMapping("/incrby")
    public String incrBy() {
        boolean b = redisServiceImpl.setNX("test_count", 100);
        logger.info("自增后返回的信息,bool={}", b);
        return "success";
    }

    @RequestMapping("/decrby")
    public String decrby() {
        for (int i = 0; i <  500; i++) {
            int count = (int) redisServiceImpl.get("test_count");
            if (count > 0) {
                long decrBy = redisServiceImpl.decrBy("test_count", 1);
                logger.info("递减后的的信息,i={},decrBy={}",i, decrBy);
            }else {
                logger.warn("库存不足，无法进行扣减");
                break;
            }
        }

        return "success";
    }

    @RequestMapping("/del")
    public String del() {
        redisServiceImpl.del("robbing_no");
        logger.info("删除成功");
        return "success";
    }

    @RequestMapping("/ip")
    public String ip(String num){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        long incr = redisServiceImpl.incrBy("limit_times", 1);
        if(incr == 1){
            redisServiceImpl.expire("limit_times", 120);
        }
        if (incr > 10){
            return "你访问的太频繁，请休息下吧......";
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entries = parameterMap.entrySet();
        for (Map.Entry<String, String[]> entry:entries){
            System.err.println(String.format("key.%s.value.%s",entry.getKey(), entry.getValue()[0]));
        }
        System.err.println("请求的uri:" + request.getRequestURI());
        System.err.println("方法名：" + request.getMethod());
        System.err.println("输出的信息为: " + incr);
        if("1".equals(num))
            System.err.println("=============");
        else
            System.err.println("++++++++++++++++++");
        return "success";
    }
}
