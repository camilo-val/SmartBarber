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
    @Column("name")
    private String nombre;
    @Column("description")
    private String descripcion;
    @Column("location")
    private String ubicacion;
    @Column("cell")
    private String celular;
    @Column("document")
    private String documento;
    @Column("tipo_documento")
    private String tipoDocumento;
    @Column("razon_social")
    private String razonSocial;
    @Column("estado")
    private String estado;
    @Column("fecha_creacion")
    private Instant fechaCreacion;
    @Column("fecha_modificacion")
    private Instant fechaModificacion;
    
}
