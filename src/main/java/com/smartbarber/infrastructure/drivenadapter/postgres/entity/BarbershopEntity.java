package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "barberia")
@ToString
public class BarbershopEntity {
    @Id
    @Column("id_barberia")
    private UUID id;
    @Column("nombre")
    private String name;
    @Column("descripcion")
    private String description;
    @Column("ubicacion")
    private String location;
    @Column("celular")
    private String phone;
    @Column("documento")
    private String document;
    @Column("tipo_documento")
    private String documentType;
    @Column("razon_social")
    private String companyName;
    @Column("estado")
    private String status;
    @Column("fecha_creacion")
    private Instant createAt;
    @Column("fecha_modificacion")
    private Instant updateAt;
    
}
