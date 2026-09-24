package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import com.smartbarber.domain.enums.TransactionStatus;
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
@Table("transaccion")
public class TransactionEntity {
    @Id
    @Column("id_transaccion")
    private UUID transactionId;
    @Column("id_reserva")
    private UUID reservationId;
    @Column("id_suscripcion_barberia")
    private UUID subscriptionBarberId;
    @Column("total")
    private BigInteger amount;
    @Column("descuento")
    private BigInteger discount;
    @Column("subtotal")
    private BigInteger subtotal;
    @Column("estado")
    private TransactionStatus status;
    @Column("id_orden")
    private UUID orderId;
    @Column("fecha_creacion")
    private Instant createdAt;
    @Column("fecha_modificacion")
    private Instant updatedAt;
}
