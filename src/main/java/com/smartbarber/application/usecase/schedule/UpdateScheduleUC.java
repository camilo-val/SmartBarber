package com.smartbarber.application.usecase.schedule;

import com.smartbarber.application.command.in.schedule.ScheduleCommand;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.schedule.ScheduleMessageExceptions;
import com.smartbarber.domain.model.schedule.Schedule;
import com.smartbarber.domain.port.SchedulePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@AllArgsConstructor
public class UpdateScheduleUC {

    private final SchedulePort schedulePort;

    public Mono<Schedule> updateSchedule(
            Integer id,
            ScheduleCommand command
    ) {
        return schedulePort.findById(id)
                .switchIfEmpty(Mono.error(
                        new BusinessExceptions(
                                ScheduleMessageExceptions.SCHEDULE_NOT_FOUND
                        )
                ))
                .flatMap(existingSchedule -> {
                    Schedule updatedSchedule = Schedule.update(
                            existingSchedule.getId(),
                            command.empleadoId(),
                            command.startTime(),
                            command.endTime(),
                            command.lunchStartTime(),
                            command.lunchEndTime(),
                            command.vacation()
                    );

                    boolean employeeChanged = !Objects.equals(
                            existingSchedule.getEmpleadoId(),
                            updatedSchedule.getEmpleadoId()
                    );

                    Mono<Boolean> employeeAlreadyHasSchedule =
                            employeeChanged
                                    ? schedulePort.existByIdEmployee(
                                    updatedSchedule.getEmpleadoId()
                            )
                                    : Mono.just(false);

                    return employeeAlreadyHasSchedule.flatMap(exists -> {
                        if (Boolean.TRUE.equals(exists)) {
                            return Mono.error(
                                    new BusinessExceptions(
                                            ScheduleMessageExceptions
                                                    .SCHEDULE_ALREADY_EXISTS
                                    )
                            );
                        }

                        return schedulePort.update(
                                id,
                                updatedSchedule
                        );
                    });
                });
    }
}