package com.smartbarber.application.usecase.subscriptionbarber;

import com.smartbarber.application.command.MessageCommand;
import com.smartbarber.application.port.subscriptionbarbershop.MessageNotificationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Component
@RequiredArgsConstructor
public class SendMessageUC {
    private final MessageNotificationPort port;

    public Mono<Void> sendMessage(MessageCommand command){
        System.out.println("Command {} -> " + command);
        return port.notification(command);
    }
}
