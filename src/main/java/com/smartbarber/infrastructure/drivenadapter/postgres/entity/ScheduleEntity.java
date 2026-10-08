package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Table(name = "horario_empleado")
@ToString
public class ScheduleEntity {

    @Id
    @Column("id_horario_empleado")
    private Integer id;
    @Column("id_empleado")
    private UUID empleadoId;
    @Column("hora_inicio")
    private LocalTime startTime;
    @Column("hora_fin")
    private LocalTime endTime;
    @Column("hora_inicio_almuerzo")
    private LocalTime lunchStartTime;
    @Column("hora_fin_almuerzo")
    private LocalTime lunchEndTime;
    @Column("vacaciones")
    private Boolean vacation;
}
