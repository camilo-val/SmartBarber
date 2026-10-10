package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "resena_reserva")
@ToString
public class ReservationReviewEntity {

    @Id
    @Column("id_resena")
    private Integer id;
    @Column("id_reserva")
    private UUID reservationId;
    @Column("calificacion")
    private Integer qualification;
    @Column("comentario")
    private String comment;
    @Column("fecha_creacion")
    private Instant createAt;
    @Column("fecha_modificacion")
    private Instant updateAt;
}
