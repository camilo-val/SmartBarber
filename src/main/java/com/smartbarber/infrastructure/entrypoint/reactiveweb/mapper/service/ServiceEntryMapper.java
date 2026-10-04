package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.service;

import com.smartbarber.domain.model.service.Service;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service.ServiceRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service.ServiceRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceEntryMapper {
    ServiceRsDto toResponse(Service service);
    default Service toDomain(ServiceRqDto rqDto){
        return Service.createService(
                null,
                rqDto.barberiaId(),
                rqDto.name(),
                rqDto.description(),
                rqDto.duration(),
                rqDto.price(),
                rqDto.offert(),
                rqDto.special_price()
        );
    }
}
