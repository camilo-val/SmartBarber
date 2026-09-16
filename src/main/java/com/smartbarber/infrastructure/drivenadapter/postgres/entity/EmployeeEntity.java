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
@Table(name = "empleado")
@ToString
public class EmployeeEntity {
    @Id
    @Column("id_empleado")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("id_barberia")
    private UUID barberiaId;
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
    @Column("especialidad")
    private String specialty;
    @Column("fecha_creacion")
    private Instant createAt;
    @Column("fecha_modificacion")
    private Instant updateAt;
}
