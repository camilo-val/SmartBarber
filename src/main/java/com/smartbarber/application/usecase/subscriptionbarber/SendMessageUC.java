package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.domain.port.event.MessageNotificationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Log4j2
@Component
@RequiredArgsConstructor
public class SendMessageUC {
    private final MessageNotificationPort port;

    public <T> Mono<Void> sendMessage(T command){
        return port.notification(command)
                .then();
    }
}


