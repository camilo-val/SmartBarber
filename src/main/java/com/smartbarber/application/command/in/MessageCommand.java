package com.smartbarber.application.command.in;

import lombok.Builder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

@Builder
public record MessageCommand (
        BigInteger amount,
        String currency,
        String description,
        UUID orderId){
}
