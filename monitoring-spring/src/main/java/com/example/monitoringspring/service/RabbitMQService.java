package com.example.monitoringspring.service;

import com.example.monitoringspring.dto.*;
import com.example.monitoringspring.models.Device;
import com.example.monitoringspring.repository.DeviceRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private final ObjectMapper objectMapper;
    private final DeviceConsumptionService deviceConsumptionService;
    private final DeviceService deviceService;
    private final DeviceUserService deviceUserService;
    private final WebSocketNotificationService webSocketNotificationService;

    @RabbitListener(queues = "consumption")
    public void handleConsumptionMessage(String message) {
        try {
            // Deserialize the JSON message into a Map
            Map<String, Object> messageData = objectMapper.readValue(message, Map.class);

            Long deviceId = Long.valueOf(String.valueOf(messageData.get("deviceId")));
            Double consumption = Double.valueOf(String.valueOf(messageData.get("consumption")));


            // Extract the timestamp
            int timestamp = (int) messageData.get("timestamp");


            // Convert timestamp to LocalDateTime
            LocalDateTime dateAndTime = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(timestamp),
                    ZoneId.systemDefault()
            );

            DeviceDTO device = deviceService.findDeviceIdRel(deviceId).getBody();

            log.info(device.getDeviceIdRel().toString());
            Double mhc = device.getMaximumHourlyConsumption();

            if(mhc < consumption) {
                log.info("bigger consumption");
                List<Long> userIds = deviceUserService.findUsersId(device.getId());
                for(Long userId : userIds) {
                    log.info("ID user{}", userId.toString());

                }
                // Send notifications to all associated users
                webSocketNotificationService.sendHighConsumptionNotification(deviceId, userIds, consumption, mhc);
            }

            deviceConsumptionService.create(new DeviceConsumptionRequestDTO(deviceId,dateAndTime,consumption));

            // Log the converted LocalDateTime
            log.info("Received consumption data: {}, LocalDateTime: {}", message, dateAndTime);

        } catch (Exception e) {
            log.error("Failed to process consumption message: {}", message, e);
        }
    }

    @RabbitListener(queues = "device_created")
    public void handleDeviceCreatedMessage(Map<String, Object> message) {
        try {
            Long deviceId = Long.parseLong((String) message.get("deviceId"));
            Double maxConsumption = (Double) message.get("maximumHourlyConsumption");
            deviceService.createDevice(new DeviceRequestDTO(deviceId,maxConsumption));
            log.info("Device Created: deviceId={}, maxConsumption={}", deviceId, maxConsumption);
        } catch (Exception e) {
            log.error("Failed to process device created message: {}", message, e);
        }
    }

    @RabbitListener(queues = "device_updated")
    public void handleDeviceUpdateMessage(Map<String,Object> message){
        try {
            Long deviceId = Long.parseLong((String) message.get("deviceId"));
            Double maxConsumption = (Double) message.get("maximumHourlyConsumption");
            deviceService.updateDevice(new DeviceRequestDTO(deviceId,maxConsumption));
            log.info("Device Created: deviceId={}, maxConsumption={}", deviceId, maxConsumption);
        } catch (Exception e) {
            log.error("Failed to process device created message: {}", message, e);
        }
    }

    @RabbitListener(queues = "device_deleted")
    public void handleDeviceDeletedMessage(Long deviceId){
        try{
            deviceService.deleteDevice(deviceId);
            log.info("Device Deleted: deviceId={}", deviceId);
        }catch (Exception e){
            log.error("Failed to process device deleted message: {}", deviceId,e);
        }
    }

    @RabbitListener(queues = "maped_device")
    public void handleMapedDeviceMessage(Map<String,Object> message){
    try {

        Long deviceId = Long.valueOf(String.valueOf(message.get("deviceId")));
        Long userId = Long.valueOf(String.valueOf(message.get("userId")));

        deviceUserService.create(new DeviceUserRequestDTO(deviceId,userId));

    } catch (Exception e) {
        log.error("Failed to process device created message: {}", message, e);
    }
    }

    @RabbitListener(queues = "maped_device_deleted")
    public void handleMapedDeviceDeletedMessage(Long userId){
        try{
            deviceUserService.deleteAllByUserId(userId);
            log.info("Devices deleted for : userId={}", userId);
        }catch (Exception e){
            log.error("Failed to process device deleted message: {}", userId,e);
        }
    }
}
