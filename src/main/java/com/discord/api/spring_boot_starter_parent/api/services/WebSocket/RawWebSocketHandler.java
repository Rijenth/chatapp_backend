package com.discord.api.spring_boot_starter_parent.api.services.WebSocket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/ws/channel/{channelId}")
@Component
public class RawWebSocketHandler {

    // channelId -> sessions
    private static final Map<String, Set<Session>> channelSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("channelId") String channelId) {
        channelSessions.computeIfAbsent(channelId, k -> ConcurrentHashMap.newKeySet()).add(session);
        System.out.println("✅ Client connecté au channel " + channelId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("channelId") String channelId) {
        Set<Session> sessions = channelSessions.get(channelId);
        if (sessions != null) {
            sessions.remove(session);
            System.out.println("⚠️ Client déconnecté du channel " + channelId);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.err.println("❌ Erreur WebSocket sur session " + session.getId() + ": " + throwable.getMessage());
    }

    @OnMessage
    public void onMessage(String message, @PathParam("channelId") String channelId) {
    }

    public static void broadcastToChannel(String channelId, String message) {
        Set<Session> sessions = channelSessions.get(channelId);


        if (sessions != null) {
            for (Session session : sessions) {
                System.out.println("broadcasting to :" + session);

                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
