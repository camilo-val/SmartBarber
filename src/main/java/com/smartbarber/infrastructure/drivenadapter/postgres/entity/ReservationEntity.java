package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "reserva")
@ToString
public class ReservationEntity {
    @Id
    @Column("id_reserva")
    private UUID id;
    @Column("id_cliente")
    private UUID customerId;
    @Column("id_empleado")
    private UUID employeeId;
    @Column("tipo_reserva")
    private ReservationType reservationType;
    @Column("estado")
    private ReservationStatus status;
    @Column("duracion")
    private Integer duration;
    @Column("fecha_inicio_real")
    private Instant startTime;
    @Column("fecha_fin_real")
    private Instant endTime;
    @Column("motivo_cancelacion")
    private String notes;
    @Column("fecha_creacion")
    private Instant creationDate;
    @Column("fecha_modificacion")
    private Instant updatedDate;
}
