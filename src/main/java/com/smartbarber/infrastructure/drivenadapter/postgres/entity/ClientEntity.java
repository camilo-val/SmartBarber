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
@Table(name = "cliente")
@ToString
public class ClientEntity {
    @Id
    @Column("id_cliente")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("documento")
    private String document;
    @Column("tipo_documento")
    private String documentType;
    @Column("nombre")
    private String name;
    @Column("celular")
    private String cell;
    @Column("correo")
    private String email;
    @Column("fecha_creacion")
    private Instant createAt;
    @Column("fecha_modificacion")
    private Instant updateAt;
}
