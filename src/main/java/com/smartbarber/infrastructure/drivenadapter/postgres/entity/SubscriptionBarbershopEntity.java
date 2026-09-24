package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import com.smartbarber.domain.enums.SubscriptionBarberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table("suscripcion_barberia")
public class SubscriptionBarbershopEntity {
    @Id
    @Column("id_suscripcion_barberia")
    private UUID id;
    @Column("id_barberia")
    private UUID barberId;
    @Column("id_suscripcion")
    private Integer subscriptionId;
    @Column("estado")
    private SubscriptionBarberStatus status;
    @Column("dias_duracion")
    private Integer duration;
    @Column("precio")
    private BigInteger subscriptionPrice;
    @Column("descuento")
    private Byte subscriptionDiscount;
    @Column("fecha_creacion")
    private Instant createdAt;
    @Column("fecha_modificacion")
    private Instant updatedAt;
    @Column("fecha_inicio")
    private Instant startDate;
    @Column("fecha_expiracion")
    private Instant expirationDate;
}
