package com.smartbarber.infrastructure.drivenadapter.postgres.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("notificacion")
public class NotificationEntity {

    @Id
    @Column("id_notificacion")
    private UUID id;

    @Column("id_cliente")
    private UUID customerId;

    @Column("id_reserva")
    private UUID reservationId;

    @Column("titulo")
    private String title;

    @Column("contenido")
    private String content;

    @Column("tipo_canal")
    private String channelType;

    @Column("tipo_evento")
    private String eventType;

    @Column("estado")
    private String status;

    @Column("reintentos")
    private Integer retries;

    @Column("error_mensaje")
    private String messageError;

    @Column("fecha_programada")
    private Instant scheduledDate;

    @Column("fecha_envio")
    private Instant dateSent;

    @Column("fecha_creacion")
    private Instant createAt;
}
