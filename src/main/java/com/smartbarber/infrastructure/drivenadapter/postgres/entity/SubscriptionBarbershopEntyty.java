package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table("suscripcion_barberia")
public class SubscriptionBarbershopEntyty {
    @Id
    @Column("id_suscripcion_barberia")
    private UUID id;
    @Column("id_barberia")
    private UUID barberId;
    @Column("id_suscripcion")
    private Integer subscriptionId;
    @Column("id_orden")
    private UUID orderId;
    @Column("id_transaccion")
    private UUID transactionId;
    @Column("estado")
    private SubscriptionBarberStatus status;
    @Column("valor")
    private Integer amount;
    @Column("dias_duracion")
    private Integer duration;
    @Column("fecha_creacion")
    private Instant createdAt;
    @Column("fecha_modificacion")
    private Instant updatedAt;
}
