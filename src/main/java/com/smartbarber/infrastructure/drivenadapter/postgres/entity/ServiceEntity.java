package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@Table(name = "servicio")
@ToString
public class ServiceEntity {

    @Id
    @Column("id_servicio")
    private Integer id;
    @Column("id_barberia")
    private UUID barberiaId;
    @Column("nombre")
    private String name;
    @Column("descripcion")
    private String description;
    @Column("duracion")
    private Integer duration;
    @Column("estado")
    private Boolean status;
    @Column("oferta")
    private Boolean offert;
    @Column("precio")
    private Integer price;
    @Column("precio_oferta")
    private Integer special_price;
    @Column("fecha_creacion")
    private Instant createAt;
    @Column("fecha_modificacion")
    private Instant updateAt;
}
