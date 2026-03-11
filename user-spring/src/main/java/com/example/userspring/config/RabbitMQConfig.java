package com.example.userspring.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue userDeletedQueue() {
        return new Queue("user_deleted", false);  // Non-durable queue
    }

    @Bean
    public DirectExchange userExchange() {
        return new DirectExchange("userExchange"); // Define a direct exchange
    }

    @Bean
    public Binding bindingUserDeletedQueue(Queue userDeletedQueue, DirectExchange userExchange) {
        return BindingBuilder.bind(userDeletedQueue).to(userExchange).with("user_deleted"); // Bind queue to exchange with the routing key
    }
}
