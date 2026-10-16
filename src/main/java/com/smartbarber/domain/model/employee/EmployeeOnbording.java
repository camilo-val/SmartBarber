package com.smartbarber.domain.model.employee;

import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.employee.MessageExceptionsEmployee;

public class EmployeeOnbording {
    private final EmployeeCommand employee;
    private final UserCommand  user;

    private EmployeeOnbording(EmployeeCommand employee, UserCommand user) {
        this.employee = employee;
        this.user = user;
    }
    public static EmployeeOnbording create(EmployeeCommand employee, UserCommand user) {
        if (employee == null || user == null) {
            throw new BusinessExceptions(MessageExceptionsEmployee.INVALID_DATA);
        }
        return new EmployeeOnbording(employee, user);
    }

    public EmployeeCommand getEmployee() {
        return employee;
    }

    public UserCommand getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "employeeOnboarding{" +
                "employee=" + employee +
                ", user=" + user +
                '}';
    }
}
