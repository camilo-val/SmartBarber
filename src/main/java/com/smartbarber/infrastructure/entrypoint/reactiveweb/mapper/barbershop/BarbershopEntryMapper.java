package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.barbershop;

import com.smartbarber.application.command.in.barbershop.BarbershopCommand;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarbershopEntryMapper {
    BarbershopRsDto toResponse(Barbershop barbershop);
    default BarbershopCommand toDomain(BarbershopRqDto rqDto) {
        return BarbershopCommand.builder()
                .id(null)
                .name(rqDto.name())
                .description(rqDto.description())
                .location(rqDto.location())
                .phone(rqDto.phone())
                .document(rqDto.document())
                .documentType(rqDto.documentType())
                .companyName(rqDto.companyName())
        .build();
    }
}
