package com.smartbarber.infrastructure.drivenadapter.postgres.mapper;

import com.smartbarber.domain.enums.TipoDocumento;
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

        return Employee.reconstruir(
                entity.getId(),
                entity.getUserId(),
                entity.getBarberiaId(),
                entity.getDocumento(),
                mapTipoDocumento(entity.getTipoDocumento()),
                entity.getNombre(),
                entity.getCelular(),
                entity.getCorreo(),
                entity.getEspecialidad(),
                entity.getFechaCreacion(),
                entity.getFechaModificacion()
        );
    }
    default TipoDocumento mapTipoDocumento(String tipoDocumento) {
        return tipoDocumento == null
                ? null
                : TipoDocumento.valueOf(tipoDocumento);
    }
}
