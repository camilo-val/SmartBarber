package com.smartbarber.infrastructure.drivenadapter.websocket.adapter;

import com.smartbarber.application.command.in.SubscriptionBarbershopCommand;
import com.smartbarber.domain.model.subscriptionbarber.SubscriptionBarbershop;
import com.smartbarber.domain.port.subscriptionbarbershop.MessageResponsePort;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class ResponseManager implements MessageResponsePort  {
    @Value("${payment.timeout}")
    private Long timeOut;

    private final Map<UUID, Sinks.Many<SubscriptionBarbershopCommand>>  pending =
            new ConcurrentHashMap<>();

    public Mono<SubscriptionBarbershopCommand> waitForResponse(UUID orderId) {
        log.info("Waiting for response for orderId: {}", orderId);
        Sinks.Many<SubscriptionBarbershopCommand> sink = Sinks.many().multicast().onBackpressureBuffer();
        pending.put(orderId, sink);
        return sink.asFlux()
                .next()
                .timeout(Duration.ofSeconds(timeOut))
                .doFinally(signalType -> pending.remove(orderId));
    }

    @Override
    public void completeResponse(UUID orderId, SubscriptionBarbershopCommand command) {
        Sinks.Many<SubscriptionBarbershopCommand> sink = pending.remove(orderId);
        if (sink != null) {
            sink.tryEmitNext(command);
        }
    }
}
