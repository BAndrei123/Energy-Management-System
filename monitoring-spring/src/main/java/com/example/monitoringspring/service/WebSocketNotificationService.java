package com.example.monitoringspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketNotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendHighConsumptionNotification(Long deviceId, List<Long> userIds, Double consumption, Double mhc) {
        String message = String.format(
                "Alert! Device %d exceeded MHC. Consumption: %.2f, MHC: %.2f",
                deviceId, consumption, mhc
        );

        for (Long userId : userIds) {

            // Send the message to each user's notifications topic
            messagingTemplate.convertAndSend(String.format("/topic/notifications/user-%d", userId), message);
        }
    }
}
