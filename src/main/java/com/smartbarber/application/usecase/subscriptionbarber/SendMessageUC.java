package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.domain.port.subscriptionbarbershop.MessageNotificationPort;
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
        System.out.println("ENTREEEE");
        return port.notification(command)
                .then();
    }
}


