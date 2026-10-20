
package com.smartbarber.infrastructure.entrypoint.websocket.config;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebsocketManager {

    private final Map<String, WebSocketSession> sessions =
            new ConcurrentHashMap<>();

    public void createSession(
            WebSocketSession session,
            String uid
    ) {
        if (session != null && uid != null && !uid.isBlank()) {
            sessions.put(uid, session);
        }
    }

    public WebSocketSession getSession(String uid) {
        if (uid == null) {
            return null;
        }

        return sessions.get(uid);
    }

    public void removeSession(String uid) {
        if (uid != null) {
            sessions.remove(uid);
        }
    }
}
