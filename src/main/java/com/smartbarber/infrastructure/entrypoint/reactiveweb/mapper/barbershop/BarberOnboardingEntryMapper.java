package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.barbershop;

import com.smartbarber.application.command.in.barbershop.BarberShopOnboardingCommand;
import com.smartbarber.application.command.in.barbershop.BarbershopCommand;
import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.barbershop.BarberShopOnboarding;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopOnBoardingRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopOnBoardingRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeOnboardingRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BarberOnboardingEntryMapper {

    default BarberShopOnboardingCommand toCommand(
            BarbershopOnBoardingRqDto dto,
            String firebaseId) {

        if (dto == null) {
            return null;
        }

        return BarberShopOnboardingCommand.builder()
                .barbershop(
                        BarbershopCommand.builder()
                                .name(dto.name())
                                .description(dto.description())
                                .location(dto.location())
                                .phone(dto.phone())
                                .document(dto.document())
                                .documentType(dto.documentType())
                                .companyName(dto.companyName())
                                .build()
                )
                .user(
                        UserCommand.builder()
                                .firebaseId(firebaseId)
                                .roleId(dto.employee().user().roleId())
                                .build()
                )
                .employee(
                        EmployeeCommand.builder()
                                .name(dto.employee().name())
                                .document(dto.employee().document())
                                .documentType(dto.employee().documentType())
                                .cell(dto.employee().cell())
                                .email(dto.employee().email())
                                .specialty(dto.employee().specialty())
                                .build()
                )
                .build();
    }

    default BarbershopOnBoardingRsDto toResponse(
            BarberShopOnboarding domain) {

        if (domain == null) {
            return null;
        }

        return BarbershopOnBoardingRsDto.builder()
                .name(domain.getBarbershop().name())
                .description(domain.getBarbershop().description())
                .location(domain.getBarbershop().location())
                .phone(domain.getBarbershop().phone())
                .document(domain.getBarbershop().document())
                .documentType(domain.getBarbershop().documentType())
                .companyName(domain.getBarbershop().companyName())
                .employee(
                        EmployeeOnboardingRsDto.builder()
                                .name(domain.getEmployee().name())
                                .document(domain.getEmployee().document())
                                .documentType(domain.getEmployee().documentType())
                                .cell(domain.getEmployee().cell())
                                .email(domain.getEmployee().email())
                                .specialty(domain.getEmployee().specialty())
                                .user(
                                        UserRsDto.builder()
                                                .id(domain.getUser().id())
                                                .firebaseId(domain.getUser().firebaseId())
                                                .status(domain.getUser().status())
                                                .roleId(domain.getUser().roleId())
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}