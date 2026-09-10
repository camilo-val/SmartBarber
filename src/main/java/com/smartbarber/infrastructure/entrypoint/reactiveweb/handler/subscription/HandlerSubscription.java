package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.subscription;

import com.smartbarber.application.usecase.subscription.CreateSubscriptionUC;
import com.smartbarber.application.usecase.subscription.FindSubscriptionUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.subscription.SubscriptionRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.subscription.SubscriptionEntryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class HandlerSubscription {
    private final CreateSubscriptionUC createSubscriptionUC;
    private final FindSubscriptionUC findSubscriptionUC;
    private final SubscriptionEntryMapper mapper;

    public Mono<ServerResponse> createSubscription(ServerRequest request){
        return request.bodyToMono(SubscriptionRqDto.class)
                .map(mapper::toCommand)
                .flatMap(createSubscriptionUC::createSubscription)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> getSubscription (ServerRequest request){
        String id = request.pathVariable("id");
        return findSubscriptionUC.findSubscriptionById(id)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> getAllSubscription (ServerRequest request){
        return findSubscriptionUC.findSubscriptionAll()
                .map(mapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

}
