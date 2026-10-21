package com.smartbarber.domain.enums;

import java.util.Arrays;

public enum RoleType {
    ADMIN(1,"ADMIN"),
    BARBER(2,"BARBER"),
    CUSTOMER(3,"CUSTOMER");
    private final int id;
    private final String name;

    RoleType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static RoleType fromId(Short roleId) {
        return Arrays.stream(values())
                .filter(role -> role.getId() == roleId)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No existe un RoleType para el id: " + roleId
                        ));
    }
}
