package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.barbershop;

import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarbershopEntryMapper {
    BarbershopRsDto toResponse(Barbershop barbershop);
    default Barbershop toDomain(BarbershopRqDto rqDto) {
        return Barbershop.createBarbershop(
                null,
                rqDto.name(),
                rqDto.description(),
                rqDto.location(),
                rqDto.cell(),
                rqDto.document(),
                rqDto.documentType(),
                rqDto.companyName()
        );
    }
}
