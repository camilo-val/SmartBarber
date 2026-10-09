package com.smartbarber.domain.model.user;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.user.UserMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String firebaseId;
    private final String status;
    private final Instant createAt;
    private final Instant updateAt;
    private final Short roleId;

    private User(UUID id, String firebaseId, String status, Instant createAt, Instant updateAt, Short roleId){
        this.id = id;
        this.firebaseId = firebaseId;
        this.status = status;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.roleId = roleId;
    }

    public static User createUserString (String firebaseId, Short roleId){
        return new User(
                null,
                firebaseId,
                "Activo",
                Instant.now(),
                null,
                roleId
        );
    }

    public static User update(UUID id, String firebaseId, String status, Instant createAt, Instant updateAt, Short roleId){
        if (id == null) {
            throw new BusinessExceptions(
                    UserMessageExceptions.DATOS_INVALIDOS
            );
        }
        return new User(
                id,
                firebaseId,
                status,
                createAt,
                updateAt,
                roleId
        );
    }

    public static User rebuild(UUID id, String firebaseId, String status, Instant createAt, Instant updateAt, Short roleId){
        if (id == null || status == null || status.isBlank()){
            throw new BusinessExceptions(UserMessageExceptions.DATOS_INVALIDOS);
        }
        return new User(
                id,
                firebaseId,
                status,
                createAt,
                updateAt,
                roleId
        );
    }

    private static boolean isNullOrBlank(String texto){ return texto == null || texto.isBlank(); }

    public UUID getId() { return id; }

    public String getFirebaseId() { return firebaseId; }

    public String getStatus() {
        return status;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }

    public Short getRoleId(){return roleId;}
}

