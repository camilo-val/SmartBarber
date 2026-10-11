package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.employee;

import com.smartbarber.application.command.in.customer.CustomerCommand;
import com.smartbarber.application.command.in.customer.CustomerOnboardingCommand;
import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.employee.EmployeeOnboardingCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.employee.EmployeeOnbording;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeOnboardingRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeOnboardingRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeOnboardingMapper {


    default EmployeeOnboardingRsDto toResponse(
            EmployeeOnbording domain) {

        if (domain == null) {
            return null;
        }

        return EmployeeOnboardingRsDto.builder()
                .id(domain.getEmployee().id())
                .name(domain.getEmployee().name())
                .document(domain.getEmployee().document())
                .documentType(domain.getEmployee().documentType())
                .cell(domain.getEmployee().cell())
                .email(domain.getEmployee().email())
                .specialty(domain.getEmployee().specialty())
                .createAt(domain.getEmployee().createAt())
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

    default EmployeeOnboardingCommand toDomain(EmployeeOnboardingRqDto dto, String firebaseId) {
        if (dto == null) {
            return null;
        }

        return EmployeeOnboardingCommand.builder()
                .employee(
                        EmployeeCommand.builder()
                                .barberiaId(dto.barberiaId())
                                .name(dto.name())
                                .document(dto.document())
                                .documentType(dto.documentType())
                                .cell(dto.cell())
                                .email(dto.email())
                                .specialty(dto.specialty())
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
}
