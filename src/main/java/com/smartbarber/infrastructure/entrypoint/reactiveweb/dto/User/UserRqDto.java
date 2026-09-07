package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.apache.logging.log4j.message.Message;

@Builder
public record UserRqDto (
        @NotNull(message = "El id es obligatorio")
        String firebaseId
){
}
