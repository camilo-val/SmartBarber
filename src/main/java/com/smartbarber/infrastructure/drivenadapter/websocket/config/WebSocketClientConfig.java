package com.smartbarber.infrastructure.drivenadapter.websocket.config;

import com.smartbarber.infrastructure.drivenadapter.websocket.adapter.WebsocketSubscriptionAdapter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebSocketClientConfig {
    private final WebsocketSubscriptionAdapter   adapter;

    @PostConstruct
    public void start(){
        System.out.println("ENTREEE");
        adapter.connect().subscribe();
    }
}
