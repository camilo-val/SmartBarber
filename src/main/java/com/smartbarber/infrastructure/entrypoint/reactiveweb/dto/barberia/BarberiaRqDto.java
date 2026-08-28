package com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barberia;

import com.smartbarber.domain.enums.TipoDocumento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;


@Builder
public record BarberiaRqDto(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        @NotBlank(message = "La descripcion es obligatoria")
        String descripcion,
        @NotBlank(message = "La ubicacion es obligatoria")
        String ubicacion,
        @NotBlank(message = "El celuar es obligatorio")
        @Pattern(regexp = "^[0-9]{10}$", message = "El celular no puede ser allfanumerico y debe tener 10 digitos")
        String celular,
        @NotBlank(message = "El documento es obligatorio")
        String documento,
        @NotNull
        TipoDocumento tipoDocumento,
        @NotBlank(message = "La razon social es obligatoria")
        String razonSocial){
}
