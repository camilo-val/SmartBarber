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

    public static Role createRole(Short id, RoleType roleType) {
        validateRoleType(roleType);

        return new Role(
                id,
                roleType,
                "Activo"
        );
    }

    public static Role update(
            Short id,
            RoleType roleType,
            String status
    ) {
        if (id == null) {
            throw new BusinessExceptions(
                    RoleMessageExceptions.INVALID_DATA
            );
        }

        validateRoleType(roleType);
        validateStatus(status);

        return new Role(id, roleType, status);
    }

    public static Role rebuild(
            Short id,
            RoleType roleType,
            String status
    ) {
        if (id == null) {
            throw new BusinessExceptions(
                    RoleMessageExceptions.INVALID_DATA
            );
        }

        validateRoleType(roleType);
        validateStatus(status);

        return new Role(id, roleType, status);
    }

    private static void validateRoleType(RoleType roleType) {
        if (roleType == null) {
            throw new BusinessExceptions(
                    RoleMessageExceptions.INVALID_ROLE
            );
        }
    }

    private static void validateStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new BusinessExceptions(
                    RoleMessageExceptions.INVALID_DATA
            );
        }
    }

    public Short getId() {
        return id;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleType=" + roleType +
                ", status='" + status + '\'' +
                '}';
    }
}
