package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Table(name = "usuario")
@ToString
public class UserEntity {
    @Id
    @Column("user_id")
    private UUID id;
    @Column("uid_firebase")
    private String firebaseId;
    @Column("estado")
    private String estado;
    @Column("fecha_creacion")
    private LocalDate fechaCreacion;
    @Column("fecha_modificacion")
    private LocalDate fechaModificacion;
}
