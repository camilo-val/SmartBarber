package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.employee;

import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.employee.EmployeeRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntryMapper {
    EmployeeRsDto toResponse(Employee employee);
    default Employee toDomain(EmployeeRqDto rqDto){
        return Employee.crear(
                null,
                rqDto.userId(),
                rqDto.barberiaId(),
                rqDto.documento(),
                rqDto.tipoDocumento(),
                rqDto.nombre(),
                rqDto.celular(),
                rqDto.correo(),
                rqDto.especialidad()
        );
    }
}
