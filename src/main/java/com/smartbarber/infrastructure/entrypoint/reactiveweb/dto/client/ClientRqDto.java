package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client;

import com.smartbarber.domain.enums.DocumentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ClientRqDto(

        @NotNull(message = "El id del usuario es obligatorio")
        UUID userId,
        @NotBlank(message = "El name es obligatorio")
        String nombre,
        @NotBlank(message = "El document es obligatorio")
        String documento,
        @NotNull
        DocumentType tipoDocumento,
        @NotBlank(message = "El celuar es obligatorio")
        @Pattern(regexp = "^[0-9]{10}$", message = "El cell no puede ser allfanumerico y debe tener 10 digitos")
        String celular,
        @NotBlank(message = "El Correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String correo
){
}
