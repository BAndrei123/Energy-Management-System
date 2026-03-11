package com.example.devicesspring.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
@AllArgsConstructor
public class DeviceUserEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public void sendMapedDevice(Long userId, Long deviceId) {
        Map<String,Object> message = Map.of("deviceId", deviceId, "userId", userId);

        rabbitTemplate.convertAndSend("maped_device", message);
    }

    public void sendMapedDeviceDelete(Long userId){

        rabbitTemplate.convertAndSend("maped_device_deleted", userId);
    }

}
