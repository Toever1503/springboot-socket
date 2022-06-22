package com.socket;

import org.springframework.util.StreamUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChatMessageHandler extends TextWebSocketHandler {

    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());

    /*
     * (non-Javadoc)
     * String is unique username
     */
    Map<String, WebSocketSession> userSessions = new HashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

//        if (!userSessions.containsKey(""))
            webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessions.remove(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(new WebSocketMessage<Object>() {
                @Override
                public Object getPayload() {
                    return "all";
                }

                @Override
                public int getPayloadLength() {
                    return 0;
                }

                @Override
                public boolean isLast() {
                    return false;
                }
            });
        }

    }

    public void sendMessage(String topic, String user, WebSocketMessage<?> message)  throws Exception{
        for (WebSocketSession webSocketSession : webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
    }

    public static void main(String[] args) {

        byte[] bytes = Base64.getDecoder().decode(base64.getBytes(StandardCharsets.UTF_8));
        try (InputStream inputStream = new ByteArrayInputStream(bytes)) {
            FileOutputStream os = new FileOutputStream("./image/test.gif");
            os.write(inputStream.readAllBytes());

            inputStream.close();
            os.close();
        } catch (IOException e) {
            // handle
            e.printStackTrace();
        }
    }

}