package com.example.chatspring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Message {

    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Boolean seen;
    private Status status;
}
