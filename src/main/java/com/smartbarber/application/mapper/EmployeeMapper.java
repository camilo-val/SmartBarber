package com.smartbarber.application.mapper;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.domain.model.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeCommand toCommand(Employee employee);
}
