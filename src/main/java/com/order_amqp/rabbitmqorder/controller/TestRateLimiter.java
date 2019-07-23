package com.order_amqp.rabbitmqorder.controller;

import com.google.common.util.concurrent.RateLimiter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestRateLimiter implements Runnable {

    private int count;

    public TestRateLimiter(int count) {
        this.count = count;
    }

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final RateLimiter limiter = RateLimiter.create(10); // 允许每秒最多1个任务

    public static void main(String[] arg) {
        for (int i = 0; i < 20; i++) {
            limiter.acquire(); // 请求令牌,超过许可会被阻塞
            if(!limiter.tryAcquire(1000, TimeUnit.MILLISECONDS)){
                System.err.println("短期无法获取到许可,无法进行下面活动");
                return;

            }
            Thread t = new Thread(new TestRateLimiter(i));
            t.start();
        }
    }

    public void run() {
        System.err.println(sdf.format(new Date()) + " Task End.." + count);
    }
}
