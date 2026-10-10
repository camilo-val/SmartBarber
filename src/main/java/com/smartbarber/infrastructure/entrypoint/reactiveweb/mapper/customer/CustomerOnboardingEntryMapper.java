package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.customer;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.application.command.in.customer.CustomerOnboardingCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.customer.CustomerOnboarding;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerOnboardingRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerOnboardingRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerOnboardingEntryMapper {

    default CustomerOnboardingCommand toCommand(
            CustomerOnboardingRqDto dto,
            String firebaseId) {

        if (dto == null) {
            return null;
        }

        return CustomerOnboardingCommand.builder()
                .customer(
                        CustomerCommand.builder()
                                .name(dto.name())
                                .document(dto.document())
                                .documentType(dto.documentType())
                                .cell(dto.cell())
                                .email(dto.email())
                                .build()
                )
                .user(
                        UserCommand.builder()
                                .firebaseId(firebaseId)
                                .roleId(dto.user().roleId())
                                .build()
                )
                .build();
    }

    default CustomerOnboardingRsDto toResponse(
            CustomerOnboarding domain) {

        if (domain == null) {
            return null;
        }

        return CustomerOnboardingRsDto.builder()
                                .id(domain.getCustomer().id())
                                .name(domain.getCustomer().name())
                                .document(domain.getCustomer().document())
                                .documentType(domain.getCustomer().documentType())
                                .cell(domain.getCustomer().cell())
                                .email(domain.getCustomer().email())
                                .createAt(domain.getCustomer().createAt())
                                .user(
                                        UserRsDto.builder()
                                                .id(domain.getUser().id())
                                                .firebaseId(domain.getUser().firebaseId())
                                                .status(domain.getUser().status())
                                                .roleId(domain.getUser().roleId())
                                                .createAt(domain.getUser().createAt())
                                                .build()
                                )
                                .build();
    }
}