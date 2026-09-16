package com.smartbarber.application.command.in;

import lombok.Builder;

@Builder
public record SubscriptionCommand (
        Integer id,
        String name,
        String description,
        Integer price
){
}
