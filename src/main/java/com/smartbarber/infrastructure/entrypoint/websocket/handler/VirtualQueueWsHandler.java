package com.smartbarber.infrastructure.entrypoint.websocket.handler;

import com.smartbarber.application.usecase.queue.SubscribeQueueUC;
import com.smartbarber.infrastructure.entrypoint.utils.commons.ObjectMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VirtualQueueWsHandler
        implements WebSocketHandler {

    private final SubscribeQueueUC useCase;
    private final ObjectMessageMapper mapper;

    @Override
    public Mono<Void> handle(
            WebSocketSession session
    ) {

        String barberShopId =
                getBarberShopId(session);

        return session.send(

                useCase.execute(
                                barberShopId
                        )
                        .map(mapper::writeValueAsString)
                        .map(session::textMessage)

        );
    }

    private String getBarberShopId(
            WebSocketSession session
    ) {

        return UriComponentsBuilder
                .fromUri(
                        session
                                .getHandshakeInfo()
                                .getUri()
                )
                .build()
                .getQueryParams()
                .getFirst("barbershopId");
    }

}