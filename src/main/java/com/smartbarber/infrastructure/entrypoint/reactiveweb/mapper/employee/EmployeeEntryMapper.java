package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.employee;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.customer.CustomerOnboarding;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.domain.model.employee.EmployeeOnbording;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerOnboardingRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeOnboardingRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeOnboardingRsDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntryMapper {
    EmployeeRsDto toResponse(Employee employee);
    default EmployeeCommand toDomain(EmployeeRqDto rqDto){
        return EmployeeCommand.builder()
                .id(null)
                .userId(rqDto.userId())
                .barberiaId(rqDto.barberiaId())
                .document(rqDto.document())
                .documentType(rqDto.documentType())
                .name(rqDto.name())
                .cell(rqDto.cell())
                .email(rqDto.email())
                .specialty(rqDto.specialty())
                .build();
    }


}
