package com.smartbarber.application.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SubscriptionCommand (
        Integer id,
        String name,
        String description,
        Integer price
){
}
