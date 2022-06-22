package com.socket;

import com.socket.message.EAction;
import com.socket.message.EMessageTopic;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.stream.Collectors;

public class MessageHandler implements WebSocketHandler {
    private final List<String> actions = Arrays.stream(EAction.values()).map(Enum::name).collect(Collectors.toList());
    private final List<String> topics = Arrays.stream(EMessageTopic.values()).map(Enum::name).collect(Collectors.toList());

    public static Map<String, WebSocketSession> userSessions = new java.util.concurrent.ConcurrentHashMap<>(); // username -> session

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Map<String, Object> data = session.getAttributes();
        data.put("topic", new ArrayList<>());
        data.put("username", "master");// replace master by Security Util get username
        userSessions.put("user", session);  // replace user by Security Util get username
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        userSessions.remove(session.getAttributes().get("username"));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    boolean isValidAction(String action) {
        return this.actions.contains(action);
    }

    boolean isValidTopic(String topic) {
        return this.topics.contains(topic);
    }

    boolean isContainTopic(WebSocketSession session, String topic) {
        return this.getTopicsForSession(session).contains(topic);
    }

    boolean hasRoomId(WebSocketSession session, String roomId) {
        Optional<Object> room = Optional.ofNullable(this.getAttributesForSession(session).get("roomId"));
        if (room.isPresent()) {
            return room.get().equals(roomId);
        }
        return false;
    }

    List<String> getTopicsForSession(WebSocketSession session) {
        return (List<String>) this.getAttributesForSession(session).get("topics");
    }

    Map<String, Object> getAttributesForSession(WebSocketSession session) {
        return session.getAttributes();
    }

}