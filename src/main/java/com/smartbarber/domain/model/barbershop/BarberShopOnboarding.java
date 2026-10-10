package com.smartbarber.domain.model.barbershop;

import com.smartbarber.application.command.in.barbershop.BarbershopCommand;
import com.smartbarber.application.command.in.employee.EmployeeCommand;
import com.smartbarber.application.command.in.user.UserCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barbershop.BarberShopMessageExceptions;

public class BarberShopOnboarding{
    private final BarbershopCommand barbershop;
    private final UserCommand user;
    private final EmployeeCommand employee;

    private BarberShopOnboarding(BarbershopCommand barbershop, UserCommand user, EmployeeCommand employee){
        this.barbershop = barbershop;
        this.user = user;
        this.employee = employee;
    }

    public static BarberShopOnboarding create(BarbershopCommand barbershop, UserCommand user, EmployeeCommand employee){
        if (barbershop == null || user == null || employee == null){
            throw new BusinessExceptions(BarberShopMessageExceptions.INVALID_DATA);
        }
        return new BarberShopOnboarding(barbershop, user, employee);
    }

    public BarbershopCommand getBarbershop() {
        return barbershop;
    }

    public UserCommand getUser() {
        return user;
    }

    public EmployeeCommand getEmployee() {
        return employee;
    }

    @Override
    public String toString() {
        return "BarberShopOnboarding{" +
                "barbershop=" + barbershop +
                ", user=" + user +
                ", employee=" + employee +
                '}';
    }
}
