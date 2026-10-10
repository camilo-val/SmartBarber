package com.smartbarber.domain.enums;

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
}
