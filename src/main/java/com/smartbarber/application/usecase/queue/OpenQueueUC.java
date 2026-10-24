
package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.enums.NotificationType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.barberqueue.BarberQueueMessageExceptions;
import com.smartbarber.domain.model.barberqueue.BarberQueue;
import com.smartbarber.domain.model.barbershop.Barbershop;
import com.smartbarber.domain.model.barbershop.BarbershopQueueResponse;
import com.smartbarber.domain.model.employee.Employee;
import com.smartbarber.domain.model.employee.EmployeeQueueResponse;
import com.smartbarber.domain.model.queue.notification.Notification;
import com.smartbarber.domain.port.BarberShopRepositoryPort;
import com.smartbarber.domain.port.EmployeePort;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.domain.port.queue.notification.NotificationEventPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OpenQueueUC {

    private final EmployeePort employeePort;
    private final BarberQueuePort barberQueuePort;
    private final BarberShopRepositoryPort barberShopRepositoryPort;
    private final NotificationEventPort notificationEventPort;
    private final TransactionalOperator transactionalOperator;

    public Mono<Void> execute(UUID employeeId) {
        Mono<BarberQueue> operation = employeePort
                .findById(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException(
                                "No existe el empleado/barbero indicado"
                        )
                ))
                .flatMap(employee ->
                        barberQueuePort.existsByEmployeeIdAndBarbershopIdAndAcceptingClients(employee.getId(),
                                employee.getBarberiaId(), true)
                        .flatMap(exists -> exists
                                ? Mono.error(() -> new BusinessExceptions(BarberQueueMessageExceptions.THE_BARBER_HAS_CONFIGURATION_ALREADY))
                                :Mono.just(employee))
                        )
                .flatMap(employee ->
                        barberQueuePort.ensureExists(
                                        employee.getId(),
                                        employee.getBarberiaId()
                                )
                                .then(barberQueuePort
                                        .findByEmployeeIdForUpdate(employeeId))
                                .switchIfEmpty(Mono.error(
                                        new IllegalStateException(
                                                "No fue posible crear o bloquear la cola del barbero"
                                        )
                                ))
                                .flatMap(locked ->
                                        barberQueuePort.updateAcceptingClients(
                                                        employeeId,
                                                        true
                                                )
                                                .flatMap(rows -> rows > 0
                                                                ? barberQueuePort.findByEmployeeId(
                                                                employeeId
                                                        )
                                                                : Mono.error(
                                                                new IllegalStateException(
                                                                        "No fue posible abrir la cola"
                                                                )
                                                        )
                                                )
                                                .flatMap(entity -> barberShopRepositoryPort.findById(employee.getBarberiaId())
                                                                .map(barbershop ->
                                                                        build(barbershop, entity, employee)
                                                                ))
                                )
                );

        /*queueBroadcastService.publish(
                                employeeId,
                                "QUEUE_OPENED"*/
        return operation
                .as(transactionalOperator::transactional)
                .flatMap(notificationEventPort::publish);
    }

    private BarbershopQueueResponse build(Barbershop barbershop, Employee employee){
        return BarbershopQueueResponse.builder()
                .id(barbershop.getId())
                .name(barbershop.getName())
                .location(barbershop.getLocation())
                .employee(EmployeeQueueResponse.builder()
                        .id(employee.getId())
                        .name(employee.getName())
                        .build()
                )
                .build();
    }

    private BarberQueue build(Barbershop barbershop, BarberQueueEntity entity, Employee employee) {
        return BarberQueue.builder()
                .type(NotificationType.QUEUE_OPENED)
                .entity(entity)
                .barbershop(build(barbershop, employee))
                .build();

    }
}
