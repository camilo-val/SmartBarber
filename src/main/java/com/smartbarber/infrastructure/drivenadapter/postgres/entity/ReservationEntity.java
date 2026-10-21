
package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import com.smartbarber.domain.enums.ReservationStatus;
import com.smartbarber.domain.enums.ReservationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table("reserva")
public class ReservationEntity {

    @Id
    @Column("id_reserva")
    private UUID id;

    @Column("id_cliente")
    private UUID customerId;

    @Column("id_empleado")
    private UUID employeeId;

    @Column("id_barberia")
    private UUID barbershopId;

    @Column("tipo_reserva")
    private ReservationType reservationType;

    @Column("estado")
    private ReservationStatus status;

    @Column("duracion")
    private Integer duration;

    @Column("fecha_ingreso_cola")
    private OffsetDateTime queueEntryTime;

    @Column("fecha_inicio_real")
    private OffsetDateTime startTime;

    @Column("fecha_fin_real")
    private OffsetDateTime endTime;

    @Column("motivo_cancelacion")
    private String notes;

    @Column("fecha_creacion")
    private OffsetDateTime creationDate;

    @Column("fecha_modificacion")
    private OffsetDateTime updatedDate;
}
