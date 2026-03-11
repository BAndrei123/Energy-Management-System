package com.example.devicesspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private final DeviceUserService deviceUserService;

    @RabbitListener(queues = "user_deleted")
    public void handleUserDeleted(Long userId) {
        log.info("Received user_deleted event for userId: {}", userId);
        // Log the processing step
        try {
            deviceUserService.deleteAllByUserId(userId);
            log.info("Successfully deleted all devices for userId: {}", userId);
        } catch (Exception e) {
            log.error("Error processing userDeleted event for userId: {}", userId, e);
        }
    }

}
