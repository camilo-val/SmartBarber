package com.smartbarber.application.usecase.reservation.servicereservation;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ReservationMessageExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;
import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.model.reservation.ReservationResponse;
import com.smartbarber.domain.model.reservation.ServiceReservationResponse;
import com.smartbarber.domain.port.ServicePort;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import com.smartbarber.domain.port.reservation.ServiceReservationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FindReservationUc {
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final ServicePort servicePort;
    private final ServiceReservationPort serviceReservationPort;

    public Mono<ReservationResponse> findReservationById(UUID id) {
        return reservationRepositoryPort.findById(id).map(r -> {
                    long start = System.currentTimeMillis();
                    System.out.println("Tiempo: " +
                        (System.currentTimeMillis() - start) + " ms");
        return r;})
                .switchIfEmpty(Mono.error(() ->
                        new BusinessExceptions(ReservationMessageExceptions.RESERVATION_NOT_FOUND)))
                .flatMap(reservation -> {
                    return serviceReservationPort.findByReservationId(reservation.getId())
                            .switchIfEmpty(Mono.error(() ->
                                    new BusinessExceptions(ServiceMessageExceptions.SERVICE_NOT_FOUND)))
                            .flatMap(serviceReservation ->
                                    servicePort.findById(serviceReservation.getServiceId())
                                            .map(service -> ServiceReservationResponse
                                                    .rebuild(serviceReservation.getId(),
                                                            serviceReservation.getReservationId(),
                                                            service.getName(),
                                                            serviceReservation.getStatus(),
                                                            serviceReservation.getDuration(),
                                                            serviceReservation.getCreatedAt()))
                            ).collectList()
                            .doOnNext(serviceReservationResponses ->
                                    System.out.println("ServiceReservationResponses: " + serviceReservationResponses))
                            .map(serviceReservationResponses -> ReservationResponse.of(
                                    reservation,
                                    serviceReservationResponses
                            ));
                })

                ;
    }

    public Flux<Reservation> findByCustomerId(UUID customerId) {
        return reservationRepositoryPort.findByCustomerId(customerId)
                .count()
                .flatMapMany(count
                        -> count == 0
                        ? Flux.error(() -> new BusinessExceptions(ReservationMessageExceptions.RESERVATION_NOT_FOUND))
                        : reservationRepositoryPort.findByCustomerId(customerId));
    }
    public Flux<Reservation> findByEmployeeId(UUID employeeId) {
        return reservationRepositoryPort.findByEmployeeId(employeeId)
                .count()
                .flatMapMany(count
                        -> count == 0
                        ? Flux.error(() -> new BusinessExceptions(ReservationMessageExceptions.RESERVATION_NOT_FOUND))
                        : reservationRepositoryPort.findByEmployeeId(employeeId));
    }

    public Flux<Reservation> findAllBarberId(UUID barberId) {
        return reservationRepositoryPort.findAllBarberId(barberId)
                .count()
                .flatMapMany(count
                        -> count == 0
                        ? Flux.error(() -> new BusinessExceptions(ReservationMessageExceptions.RESERVATION_NOT_FOUND))
                        : reservationRepositoryPort.findAllBarberId(barberId));
    }
}
