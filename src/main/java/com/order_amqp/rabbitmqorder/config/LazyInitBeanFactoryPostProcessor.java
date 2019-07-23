//package com.order_amqp.rabbitmqorder.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.context.annotation.Configuration;
//
///**
// * spring主件的惰性化加载
// */
//@Configuration
//public class LazyInitBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
//    private static final Logger log = LoggerFactory.getLogger(LazyInitBeanFactoryPostProcessor.class);
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        for (String beanName : beanFactory.getBeanDefinitionNames()) {
//            log.info("springboot加载方式之惰性化加载启动...={}",beanName);
//            beanFactory.getBeanDefinition(beanName).setLazyInit(true);
//        }
//    }
//}
