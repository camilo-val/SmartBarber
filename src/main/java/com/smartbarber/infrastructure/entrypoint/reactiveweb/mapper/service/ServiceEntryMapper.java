package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.service;

import com.smartbarber.application.command.in.service.ServiceCommand;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service.ServiceRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service.ServiceRsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceEntryMapper {

    @Mapping(target = "offert", source = "offer")
    @Mapping(target = "special_price", source = "specialPrice")
    ServiceRsDto toResponse(Service service);

    default ServiceCommand toCommand(ServiceRqDto request) {
        return ServiceCommand.builder()
                .id(null)
                .barberiaId(request.barberiaId())
                .name(request.name())
                .description(request.description())
                .duration(request.duration())
                .status(null)
                .offer(request.offert())
                .price(request.price())
                .specialPrice(request.special_price())
                .createAt(null)
                .updateAt(null)
                .build();
    }
}