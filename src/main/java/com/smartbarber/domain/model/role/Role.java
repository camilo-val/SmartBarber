package com.smartbarber.domain.model.role;

import com.smartbarber.domain.enums.RoleType;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.Role.RoleMessageExceptions;

public class Role {
    private final Short id;
    private final RoleType roleType;
    private final String status;

    private Role(Short id, RoleType roleType, String status) {
        this.id = id;
        this.roleType = roleType;
        this.status = status;
    }

    public static Role createRol(Short id, RoleType roleType) {

        return new Role(
                id,
                roleType,
                "Activo"
        );
    }

    public static Role update(Short id, RoleType roleType, String status) {
        if (id == null) {
            throw new BusinessExceptions(
                    RoleMessageExceptions.INVALID_DATA
            );
        }

        return new Role(
                id,
                roleType,
                status
        );
    }

    public static Role rebuild(Short id, RoleType roleType, String status) {
        if (id == null || status == null || status.isBlank()) {
            throw new BusinessExceptions(RoleMessageExceptions.INVALID_DATA);
        }

        return new Role(id, roleType, status);
    }

    private static boolean isNullOrBlank(String text) { return text == null || text.isBlank(); }

    public Short getId() { return id; }

    public RoleType getRoleType() { return roleType; }

    public String getStatus() {
        return status;
    }
}
