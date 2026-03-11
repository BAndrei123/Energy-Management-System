package com.example.monitoringspring.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue consumtionQueue(){
        return new Queue("consumption");
    }

//    @Bean
//    public Queue deviceCreatedQueue() {
//        return new Queue("device_created", false);
//    }
//
//    @Bean
//    public Queue deviceUpdatedQueue() {
//        return new Queue("device_updated", false);
//    }
//
//    @Bean
//    public Queue deviceDeletedQueue() {
//        return new Queue("device_deleted", false);
//    }
}
