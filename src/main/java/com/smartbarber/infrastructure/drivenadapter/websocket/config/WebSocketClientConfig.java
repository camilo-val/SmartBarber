package com.smartbarber.infrastructure.drivenadapter.websocket.config;

import com.smartbarber.infrastructure.drivenadapter.websocket.adapter.ConnectionManager;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebSocketClientConfig {
    private final ConnectionManager adapter;

    @PostConstruct
    public void start(){
        adapter.connect().subscribe();
    }
}
