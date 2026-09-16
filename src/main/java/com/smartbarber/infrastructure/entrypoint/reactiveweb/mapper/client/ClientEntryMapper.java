package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.client;

import com.smartbarber.domain.model.client.Client;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client.ClientRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client.ClientRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientEntryMapper {
    ClientRsDto toResponse(Client client);
    default Client toDomain(ClientRqDto rqDto){
        return Client.createClient(
                null,
                rqDto.userId(),
                rqDto.document(),
                rqDto.documentType(),
                rqDto.name(),
                rqDto.cell(),
                rqDto.email()

        );
    }
}
