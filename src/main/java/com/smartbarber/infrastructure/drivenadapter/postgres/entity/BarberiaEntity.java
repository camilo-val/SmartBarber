package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "barberia")
@ToString
public class BarberiaEntity {
    @Id
    @Column("id_barberia")
    private UUID id;
    @Column("nombre")
    private String nombre;
    @Column("descripcion")
    private String descripcion;
    @Column("ubicacion")
    private String ubicacion;
    @Column("celular")
    private String celular;
    @Column("documento")
    private String documento;
    @Column("tipo_documento")
    private String tipoDocumento;
    @Column("razon_social")
    private String razonSocial;
    @Column("estado")
    private String estado;
    @Column("fecha_creacion")
    private LocalDate fechaCreacion;
    @Column("fecha_modificacion")
    private LocalDate fechaModificacion;
    
}
