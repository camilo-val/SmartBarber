package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserRqDto(

        @NotNull(message = "El roleId es obligatorio")
        Short roleId

) {
}