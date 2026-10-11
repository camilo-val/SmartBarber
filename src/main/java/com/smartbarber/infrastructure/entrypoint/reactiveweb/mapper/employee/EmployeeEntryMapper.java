package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.employee;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntryMapper {
    EmployeeRsDto toResponse(Employee employee);
    default EmployeeCommand toDomain(EmployeeRqDto rqDto, String firebaseId){
        return EmployeeCommand.builder()
                .id(null)
                .user(UserCommand.builder()
                        .firebaseId(firebaseId)
                        .roleId(rqDto.user().roleId())
                        .build())
                .document(rqDto.document())
                .documentType(rqDto.documentType())
                .name(rqDto.name())
                .cell(rqDto.cell())
                .email(rqDto.email())
                .specialty(rqDto.specialty())
                .build();
    }

    default EmployeeCommand toDomain(EmployeeRqDto rqDto){
        return EmployeeCommand.builder()
                .id(null)
                .document(rqDto.document())
                .documentType(rqDto.documentType())
                .name(rqDto.name())
                .cell(rqDto.cell())
                .email(rqDto.email())
                .specialty(rqDto.specialty())
                .build();
    }
}
