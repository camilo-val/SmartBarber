package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table("cola_barbero")
public class BarberQueueEntity {

    @Id
    @Column("id_cola_barbero")
    private UUID id;

    @Column("id_empleado")
    private UUID employeeId;

    @Column("id_barberia")
    private UUID barbershopId;

    @Column("aceptando_clientes")
    private Boolean acceptingClients;

    @Column("version")
    private Long version;

    @Column("fecha_creacion")
    private Instant creationDate;

    @Column("fecha_modificacion")
    private Instant updatedDate;
}