
package com.smartbarber.application.usecase.reservation.servicereservation;

import com.smartbarber.application.command.in.reservation.ReservationCommand;
import com.smartbarber.application.usecase.queue.QueueBroadcastService;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.model.reservation.ServiceReservation;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import com.smartbarber.domain.port.reservation.ServiceReservationPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class CreateReservationQueueUc {

    private final ServiceReservationPort serviceReservationPort;
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final ServicePort servicePort;
    private final BarberQueuePort barberQueuePort;
    private final QueueBroadcastService queueBroadcastService;
    private final TransactionalOperator transactionalOperator;

    public Mono<Reservation> createReservation(ReservationCommand command) {
        validateCommand(command);

        Mono<Reservation> operation = barberQueuePort
                .findByEmployeeIdForUpdate(command.employeeId())
                .switchIfEmpty(Mono.error(new IllegalStateException(
                        "El barbero no tiene una cola configurada. Debe abrirla primero."
                )))
                .flatMap(queue -> validateQueue(queue, command)
                        .then(loadServices(command.reservationServiceId()))
                        .flatMap(services -> {
                            validateServicesBelongToBarbershop(
                                    services,
                                    command.barbershopId()
                            );

                            int duration = getDuration(services);

                            Reservation reservation =
                                    Reservation.createReservationQueue(
                                            command.customerId(),
                                            command.barbershopId(),
                                            command.employeeId(),
                                            duration,
                                            command.notes()
                                    );

                            return reservationRepositoryPort.save(reservation)
                                    .flatMap(saved ->
                                            createServiceReservations(saved, services)
                                                    .thenReturn(saved)
                                    );
                        }));

        return operation.as(transactionalOperator::transactional)
                .flatMap(saved ->
                        queueBroadcastService
                                .publish(command.employeeId(), "QUEUE_UPDATED")
                                .thenReturn(saved)
                )
                .doOnSuccess(saved -> {
                    if (saved != null) {
                        log.info(
                                "Cliente agregado a cola virtual. reservationId={}, employeeId={}",
                                saved.getId(),
                                saved.getEmployeeId()
                        );
                    }
                });
    }

    private void validateCommand(ReservationCommand command) {
        if (command == null
                || command.customerId() == null
                || command.employeeId() == null
                || command.barbershopId() == null
                || command.reservationServiceId() == null
                || command.reservationServiceId().isEmpty()) {

            throw new IllegalArgumentException(
                    "Debes indicar cliente, barbero, barbería y al menos un servicio"
            );
        }
    }

    private Mono<Void> validateQueue(
            BarberQueueEntity queue,
            ReservationCommand command
    ) {
        if (!Boolean.TRUE.equals(queue.getAcceptingClients())) {
            return Mono.error(new IllegalStateException(
                    "El barbero no está aceptando clientes"
            ));
        }

        if (!Objects.equals(
                queue.getBarbershopId(),
                command.barbershopId()
        )) {
            return Mono.error(new IllegalArgumentException(
                    "El barbero no pertenece a la barbería indicada"
            ));
        }

        return Mono.empty();
    }

    private Mono<List<Service>> loadServices(List<Integer> serviceIds) {
        return Flux.fromIterable(serviceIds)
                .concatMap(id ->
                        servicePort.findById(id)
                                .switchIfEmpty(Mono.error(
                                        new BusinessExceptions(
                                                ServiceMessageExceptions.SERVICE_NOT_FOUND
                                        )
                                ))
                )
                .collectList();
    }

    private void validateServicesBelongToBarbershop(
            List<Service> services,
            UUID barbershopId
    ) {
        boolean invalid = services.stream().anyMatch(service ->
                !Boolean.TRUE.equals(service.getStatus())
                        || !Objects.equals(
                        service.getBarberiaId(),
                        barbershopId
                )
                        || service.getDuration() == null
                        || service.getDuration() <= 0
        );

        if (invalid) {
            throw new IllegalArgumentException(
                    "Uno o más servicios no están activos, "
                            + "no pertenecen a la barbería o tienen duración inválida"
            );
        }
    }

    private Mono<Void> createServiceReservations(
            Reservation reservation,
            List<Service> services
    ) {
        return Flux.fromIterable(services)
                .concatMap(service ->
                        serviceReservationPort.save(
                                ServiceReservation.createServiceReservation(
                                        reservation.getId(),
                                        service.getId(),
                                        service.getDuration()
                                )
                        )
                )
                .then();
    }

    private int getDuration(List<Service> services) {
        return services.stream()
                .map(Service::getDuration)
                .reduce(0, Integer::sum);
    }
}
