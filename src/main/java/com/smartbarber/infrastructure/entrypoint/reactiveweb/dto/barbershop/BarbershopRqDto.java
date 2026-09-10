package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop;

import com.smartbarber.domain.enums.DocumentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record BarbershopRqDto(
        @NotBlank(message = "El name es obligatorio")
        String name,
        @NotBlank(message = "La description es obligatoria")
        String description,
        @NotBlank(message = "La location es obligatoria")
        String location,
        @NotBlank(message = "El celuar es obligatorio")
        @Pattern(regexp = "^[0-9]{10}$", message = "El cell no puede ser allfanumerico y debe tener 10 digitos")
        String cell,
        @NotBlank(message = "El document es obligatorio")
        String document,
        @NotNull
        DocumentType documentType,
        @NotBlank(message = "La razon social es obligatoria")
        String companyName){
}
