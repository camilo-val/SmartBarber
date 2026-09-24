package com.smartbarber.application.command.in;

import lombok.Builder;

import java.math.BigInteger;

@Builder
public record SubscriptionCommand (
        Integer id,
        String name,
        String description,
        BigInteger price,
        Byte discount
){
}
