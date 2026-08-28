package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.barberia;

import com.smartbarber.domain.model.barberia.Barberia;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barberia.BarberiaRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barberia.BarberiaRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarberiaEntryMapper {
    BarberiaRsDto toResponse(Barberia barberia);
    default Barberia toDomain(BarberiaRqDto rqDto) {
        return Barberia.crear(
                null,
                rqDto.nombre(),
                rqDto.descripcion(),
                rqDto.ubicacion(),
                rqDto.celular(),
                rqDto.documento(),
                rqDto.tipoDocumento(),
                rqDto.razonSocial()
        );
    }
}
