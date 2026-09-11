package com.smartbarber.application.command;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record MessageCommand (
        BigDecimal amount,
        String currency,
        String description,
        UUID orderId){

}
