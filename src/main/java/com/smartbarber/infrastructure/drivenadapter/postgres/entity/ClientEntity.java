package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "cliente")
@ToString
public class ClientEntity {
    @Id
    @Column("id_cliente")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("documento")
    private String documento;
    @Column("tipo_documento")
    private String tipoDocumento;
    @Column("nombre")
    private String nombre;
    @Column("celular")
    private String celular;
    @Column("correo")
    private String correo;
    @Column("fecha_creacion")
    private LocalDate fechaCreacion;
    @Column("fecha_modificacion")
    private LocalDate fechaModificacion;
}
