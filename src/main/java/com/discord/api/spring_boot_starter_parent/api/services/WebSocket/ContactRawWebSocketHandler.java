package com.discord.api.spring_boot_starter_parent.api.services.WebSocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/{username}/contacts")
@Component
public class ContactRawWebSocketHandler {
    private static final Map<String, Set<Session>> contactSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        contactSessions.computeIfAbsent(username, k -> ConcurrentHashMap.newKeySet()).add(session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        Set<Session> sessions = contactSessions.get(username);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
    }

    public static void broadcastToContactListMainUser(String username, String payload) {
        Set<Session> sessions = contactSessions.get(username);

        if (sessions != null) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(payload);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
