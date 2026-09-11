package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("suscripcion")
public class SubscriptionEntity {
    @Id
    @Column("id_suscripcion")
    private Integer id;
    @Column("nombre")
    private String name;
    @Column("descripcion")
    private String description;
    @Column("precio")
    private Integer price;
    @Column("fecha_creacion")
    private Instant createAt;
    @Column("fecha_actualizacion")
    private Instant updateAt;
}
