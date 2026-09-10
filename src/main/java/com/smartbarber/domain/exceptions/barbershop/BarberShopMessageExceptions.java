package com.smartbarber.domain.exceptions.barbershop;

import com.smartbarber.domain.exceptions.ErrorMessage;

public enum BarberShopMessageExceptions implements ErrorMessage{
    INVALID_BARBERSHOP("BA_001","invalid barbershop"),
    BARBERSHOP_ALREADY_EXIST("BP_002","barbershop already exists"),
    INVALID_DATA("BP_003","invalid data"),
    BARBER_NOT_FOUND("BP_004","barbershop not found");

    private final String code;
    private final String message;

    BarberShopMessageExceptions(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
