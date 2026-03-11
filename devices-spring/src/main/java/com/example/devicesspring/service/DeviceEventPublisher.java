package com.example.devicesspring.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class DeviceEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendDeviceCreatedEvent(String deviceId, Double maximumHourlyConsumption) {
        // Prepare the payload
        Map<String, Object> message = Map.of(
                "deviceId", deviceId,
                "maximumHourlyConsumption", maximumHourlyConsumption
        );

        // Send the message to the specified exchange with the routing key
        rabbitTemplate.convertAndSend("deviceExchange", "device_created", message);
    }

    public void sendDeviceUpdatedEvent(String deviceId, Double maximumHourlyConsumption) {
        Map<String, Object> message = Map.of(
                "deviceId", deviceId,
                "maximumHourlyConsumption", maximumHourlyConsumption
        );

        rabbitTemplate.convertAndSend("deviceExchange", "device_updated", message);
    }

    public void sendDeviceDeletedEvent(Long deviceId) {
        rabbitTemplate.convertAndSend("deviceExchange", "device_deleted", deviceId);
    }
}
