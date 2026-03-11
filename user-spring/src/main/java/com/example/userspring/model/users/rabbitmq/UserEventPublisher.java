package com.example.userspring.model.users.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendUserDeletedEvent(Long userId) {
        System.out.println(userId);
        // Send the message to the specified exchange with the routing key
        rabbitTemplate.convertAndSend("userExchange", "user_deleted", userId);
    }
}
