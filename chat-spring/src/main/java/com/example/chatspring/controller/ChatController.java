package com.example.chatspring.controller;

import com.example.chatspring.model.Message;
import com.example.chatspring.model.Status;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class ChatController {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        messagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        return message;
    }
    @MessageMapping("/typing")
    public void handleTyping(@Payload Message message) {
        if (message.getStatus() == Status.TYPING) {
            messagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        }
    }

    @MessageMapping("/seen")
    public void handleSeen(@Payload Message message) {
        if(message.getStatus() == Status.SEEN) {
            messagingTemplate.convertAndSendToUser(message.getReceiverName(), "/private", message);
        }
    }

}
