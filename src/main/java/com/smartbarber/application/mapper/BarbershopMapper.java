package com.smartbarber.application.mapper;

import com.smartbarber.application.command.in.barbershop.BarbershopCommand;
import com.smartbarber.domain.model.barbershop.Barbershop;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarbershopMapper {
    BarbershopCommand toCommand(Barbershop barbershop);
}
