package com.smartbarber.infrastructure.drivenadapter.postgres.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "rol")
@ToString
public class RoleEntity {

    @Id
    @Column("id_rol")
    private Short id;
    @Column("nombre")
    private String roleType;
    @Column("estado")
    private String status;

}
