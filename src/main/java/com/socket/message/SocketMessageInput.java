package com.socket.message;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class SocketMessageInput {
    private EAction action;
    private EMessageTopic topic;

    private String sendTo;
    private List<String> receivers;

    private String message;
    private List<String> attachments;

    private String roomId; // is only used for chat room

    public static void main(String[] args) {
        String jsonString = "{\"name\":\"Mahesh\", \"age\":21}";
        ObjectMapper mapper = new ObjectMapper();
    }
}
