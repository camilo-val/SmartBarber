package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.employee;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntryMapper {
    EmployeeRsDto toResponse(Employee employee);
    default EmployeeCommand toDomain(EmployeeRqDto rqDto){
        return EmployeeCommand.builder()
                .id(null)
                .userId(null)
                .document(rqDto.document())
                .documentType(rqDto.documentType())
                .name(rqDto.name())
                .cell(rqDto.cell())
                .email(rqDto.email())
                .specialty(rqDto.specialty())
                .build();
    }
}
