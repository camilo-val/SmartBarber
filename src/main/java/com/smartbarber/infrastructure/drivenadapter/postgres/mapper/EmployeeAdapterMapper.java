package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.DocumentType;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface EmployeeAdapterMapper {
    EmployeeEntity toEntity(Employee employee);
    default Employee toDomain(EmployeeEntity entity){
        if (entity == null){
            return null;
        }

        return Employee.rebuild(
                entity.getId(),
                entity.getUserId(),
                entity.getBarberiaId(),
                entity.getDocument(),
                mapTipoDocumento(entity.getDocumentType()),
                entity.getName(),
                entity.getCell(),
                entity.getEmail(),
                entity.getSpecialty(),
                entity.getCreateAt(),
                entity.getUpdateAt()
        );
    }
    default DocumentType mapTipoDocumento(String documentType) {
        return documentType == null
                ? null
                : DocumentType.valueOf(documentType);
    }
}
