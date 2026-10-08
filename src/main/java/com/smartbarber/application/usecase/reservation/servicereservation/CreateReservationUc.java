package com.smartbarber.application.usecase.reservation.servicereservation;

import com.smartbarber.application.command.in.reservation.ReservationCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ReservationMessageExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.model.reservation.ServiceReservation;
import com.smartbarber.domain.model.service.Service;
import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import com.smartbarber.domain.port.reservation.ServiceReservationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class CreateReservationUc {

    private final ServiceReservationPort serviceReservationPort;
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final ServicePort servicePort;
    private final TransactionalOperator transactionalOperator;

    public Mono<Reservation> createReservation(ReservationCommand command) {
        return validateReservation(command)
                .then(loadServices(command.reservationServiceId()))
                .flatMap(services -> createReservation(command, services))
                .as(transactionalOperator::transactional);
    }

    private Mono<Void> validateReservation(ReservationCommand command) {

        Mono<Void> employeeValidation =
                reservationRepositoryPort.existsByEmployeeAndRange(
                                command.employeeId(),
                                command.startTime(),
                                command.endTime()).doOnNext(res -> log.info("Employee validation passed: {}", res))
                        .flatMap(exists -> exists
                                ? Mono.error(() -> new BusinessExceptions(
                                ReservationMessageExceptions.EMPLOYEE_NOT_AVAILABLE))
                                : Mono.empty());

        Mono<Void> customerValidation =
                reservationRepositoryPort.existsByCustomerAndRange(
                                command.customerId(),
                                command.startTime(),
                                command.endTime()).doOnNext(res -> log.info("client validation passed: {}", res))
                        .flatMap(exists -> exists
                                ? Mono.error(() -> new BusinessExceptions(
                                ReservationMessageExceptions.RESERVATION_ALREADY_EXISTS))
                                : Mono.empty()).doOnNext(res -> log.info("Customer validation passed: {}", res)).then();

        return Mono.when(employeeValidation, customerValidation);
    }

    private Mono<List<Service>> loadServices(List<Integer> serviceIds) {

        if (serviceIds.isEmpty()) {
            return Mono.error(
                    new BusinessExceptions(
                            ServiceMessageExceptions.SERVICE_NOT_FOUND
                    )
            );
        }

        return Flux.fromIterable(serviceIds)
                .flatMap(id ->
                        servicePort.findById(id)
                                .switchIfEmpty(
                                        Mono.error(
                                                new BusinessExceptions(
                                                        ServiceMessageExceptions.SERVICE_NOT_FOUND
                                                )
                                        )
                                )
                )
                .collectList();
    }

    private Mono<Reservation> createReservation(
            ReservationCommand command,
            List<Service> services) {

        Integer duration = services.stream()
                .map(Service::getDuration)
                .reduce(0, Integer::sum);

        Reservation reservation = Reservation.createReservation(
                command.customerId(),
                command.employeeId(),
                duration,
                command.startTime(),
                command.startTime().plus(duration, java.time.temporal.ChronoUnit.MINUTES),
                command.notes()
        );
        return reservationRepositoryPort.save(reservation)
                .doOnNext(res -> log.info("1 Reservation saved: {}", res))
                .flatMap(savedReservation ->
                        createServiceReservations(savedReservation, services)
                                .doOnNext(res -> log.info("Reservation saved: {}", res))
                                .then(Mono.just(savedReservation))
                ).doOnNext(res -> log.info("Final Reservation saved: {}", res));
    }

    private Mono<Void> createServiceReservations(
            Reservation reservation,
            List<Service> services) {
        return Flux.fromIterable(services)
                .flatMap(service ->
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
}