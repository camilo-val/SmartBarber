package com.smartbarber.application.usecase.queue;

import com.smartbarber.domain.enums.NotificationType;
import com.smartbarber.domain.model.customer.Customer;
import com.smartbarber.domain.model.queue.QueueSnapshot;
import com.smartbarber.domain.model.queue.event.UserEvent;
import com.smartbarber.domain.model.reservation.Reservation;
import com.smartbarber.domain.port.CustomerPort;
import com.smartbarber.domain.port.queue.BarberQueuePort;
import com.smartbarber.domain.port.queue.UserEventPublisherPort;
import com.smartbarber.domain.port.reservation.ReservationRepositoryPort;
import com.smartbarber.infrastructure.drivenadapter.postgres.entity.BarberQueueEntity;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.customer.CustomerRsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueBroadcastService {

    private final BarberQueuePort barberQueuePort;
    private final ReservationRepositoryPort reservationRepositoryPort;
    private final CustomerPort customerPort;
    private final UserEventPublisherPort userEventPublisherPort;

    private record CurrentTurnData(
            Reservation reservation,
            Customer customer
    ) {
    }

    public Mono<QueueSnapshot> getSnapshot(UUID employeeId) {
        System.out.println(
                "QueueBroadcastService.getSnapshot: employeeId="
                        + employeeId
        );
        return barberQueuePort.findByEmployeeId(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException(
                                "La cola del barbero no está configurada"
                        )
                ))
                .flatMap(queue ->
                        Boolean.TRUE.equals(queue.getAcceptingClients())
                                ? buildSnapshot(queue)
                                : Mono.error(
                                new IllegalStateException(
                                        "La cola del barbero no está aceptando clientes"
                                )
                        )
                );
    }

    private Mono<QueueSnapshot> getSnapshots(UUID employeeId) {
        return barberQueuePort.findByEmployeeId(employeeId)
                .switchIfEmpty(Mono.error(
                        new IllegalStateException(
                                "La cola del barbero no está configurada"
                        )
                ))
                .flatMap(this::buildSnapshot);
    }

    public Mono<Void> publish(UUID employeeId, NotificationType eventType) {

        System.out.println(
                "QueueBroadcastService.publish: employeeId="
                        + employeeId
                        + ", eventType="
                        + eventType
        );

        return getSnapshots(employeeId)
                .doOnNext(snapshot ->
                        userEventPublisherPort.publish(
                                employeeId.toString(),
                                UserEvent.builder()
                                        .type(eventType.name())
                                        .payload(snapshot)
                                        .build()
                        )
                )
                .then();
    }

    private Mono<QueueSnapshot> buildSnapshot(
            BarberQueueEntity queue
    ) {

        UUID employeeId = queue.getEmployeeId();

        Mono<Optional<CurrentTurnData>> currentTurnMono =
                reservationRepositoryPort
                        .findCurrentQueueTurn(employeeId)
                        .flatMap(reservation ->
                                customerPort.findById(
                                                reservation.getCustomerId()
                                        )
                                        .map(customer ->
                                                new CurrentTurnData(
                                                        reservation,
                                                        customer
                                                )
                                        )
                        )
                        .map(Optional::of)
                        .defaultIfEmpty(Optional.empty());

        Mono<List<Reservation>> waitingMono =
                reservationRepositoryPort
                        .findWaitingQueueByEmployeeId(employeeId)
                        .collectList();

        return Mono.zip(
                        currentTurnMono,
                        waitingMono
                )
                .flatMap(tuple -> {

                    Optional<CurrentTurnData> currentOptional =
                            tuple.getT1();

                    List<Reservation> waiting =
                            tuple.getT2();

                    Reservation current =
                            currentOptional
                                    .map(CurrentTurnData::reservation)
                                    .orElse(null);

                    Customer currentCustomer =
                            currentOptional
                                    .map(CurrentTurnData::customer)
                                    .orElse(null);

                    int remainingMinutes =
                            estimateRemainingMinutes(current);

                    QueueSnapshot.QueueTurn currentTurn =
                            current == null
                                    ? null
                                    : new QueueSnapshot.QueueTurn(
                                    current.getId(),
                                    buildCustomer(currentCustomer),
                                    0,
                                    0,
                                    current.getDuration(),
                                    current.getStatus().name()
                            );

                    List<Mono<QueueSnapshot.QueueTurn>> waitingTurns =
                            new ArrayList<>();

                    int accumulatedMinutes =
                            remainingMinutes;

                    for (int i = 0; i < waiting.size(); i++) {

                        Reservation reservation =
                                waiting.get(i);

                        int position = i + 1;

                        int estimatedWait =
                                accumulatedMinutes;

                        if (reservation.getDuration() != null) {
                            accumulatedMinutes +=
                                    reservation.getDuration();
                        }

                        Mono<QueueSnapshot.QueueTurn> turnMono =
                                customerPort
                                        .findById(
                                                reservation.getCustomerId()
                                        )
                                        .map(customer ->
                                                new QueueSnapshot.QueueTurn(
                                                        reservation.getId(),
                                                        buildCustomer(customer),
                                                        position,
                                                        estimatedWait,
                                                        reservation.getDuration(),
                                                        reservation.getStatus().name()
                                                )
                                        )
                                        .defaultIfEmpty(
                                                new QueueSnapshot.QueueTurn(
                                                        reservation.getId(),
                                                        null,
                                                        position,
                                                        estimatedWait,
                                                        reservation.getDuration(),
                                                        reservation.getStatus().name()
                                                )
                                        );

                        waitingTurns.add(turnMono);
                    }

                    return Flux.mergeSequential(waitingTurns)
                            .collectList()
                            .map(turns ->
                                    new QueueSnapshot(
                                            employeeId,
                                            Boolean.TRUE.equals(
                                                    queue.getAcceptingClients()
                                            ),
                                            currentTurn,
                                            List.copyOf(turns),
                                            OffsetDateTime.now()
                                    )
                            );
                });
    }

    private int estimateRemainingMinutes(
            Reservation current
    ) {

        if (current == null
                || current.getDuration() == null) {
            return 0;
        }

        if (current.getStartTime() == null) {
            return current.getDuration();
        }

        long elapsed = Duration.between(
                current.getStartTime(),
                OffsetDateTime.now()
        ).toMinutes();

        return Math.max(
                0,
                current.getDuration() - (int) elapsed
        );
    }

    private CustomerRsDto buildCustomer(
            Customer customer
    ) {

        if (customer == null) {
            return null;
        }

        return CustomerRsDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .cell(customer.getCell())
                .build();
    }
}