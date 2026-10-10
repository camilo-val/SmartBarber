package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.customer;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerEntryMapper {
    CustomerRsDto toResponse(Customer customer);
    default CustomerCommand toDomain(CustomerRqDto rqDto){
        if(rqDto == null){
            return null;
        }
        return CustomerCommand.builder()
                        .userId(rqDto.userId())
                        .document(rqDto.document())
                        .documentType(rqDto.documentType())
                        .name(rqDto.name())
                        .cell(rqDto.cell())
                        .email(rqDto.email())
                .build();

    }
}
