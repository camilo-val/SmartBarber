package com.smartbarber.domain.model.service;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.service.ServiceMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Service {

    private final Integer id;
    private final UUID barberiaId;
    private final String name;
    private final String description;
    private final Integer duration;
    private final Boolean status;
    private final Boolean offert;
    private final Integer price;
    private final Integer special_price;
    private final Instant createAt;
    private final Instant updateAt;

    private Service(Integer id, UUID barberiaId, String name, String description, Integer duration,
                    Boolean status, Boolean offert, Integer price, Integer special_price, Instant createAt,
                    Instant updateAt){

        this.id = id;
        this.barberiaId = barberiaId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.status = status;
        this.offert = offert;
        this.price = price;
        this.special_price = special_price;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static Service createService(
            Integer id,
            UUID barberiaId,
            String name,
            String description,
            Integer duration,
            Integer price,
            Boolean offert,
            Integer special_price
    ) {

        validateInputs(name, description);

        if (Boolean.TRUE.equals(offert) && special_price == null) {
            throw new BusinessExceptions(
                    ServiceMessageExceptions.INVALID_DATA
            );
        }

        if (Boolean.FALSE.equals(offert)) {
            special_price = null;
        }

        return new Service(
                id,
                barberiaId,
                name,
                description,
                duration,
                true,
                offert,
                price,
                special_price,
                Instant.now(),
                null
        );
    }

    public static Service update(
            Integer id,
            UUID barberiaId,
            String name,
            String description,
            Integer duration,
            Boolean status,
            Boolean offert,
            Integer price,
            Integer special_price,
            Instant createAt
    ) {

        if (id == null) {
            throw new BusinessExceptions(
                    ServiceMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(name, description);

        if (Boolean.TRUE.equals(offert) && special_price == null) {
            throw new BusinessExceptions(
                    ServiceMessageExceptions.INVALID_DATA
            );
        }

        if (Boolean.FALSE.equals(offert)) {
            special_price = null;
        }

        return new Service(
                id,
                barberiaId,
                name,
                description,
                duration,
                status,
                offert,
                price,
                special_price,
                createAt,
                Instant.now()
        );
    }

    public static Service rebuild(Integer id, UUID barberiaId, String name, String description, Integer duration,
                                  Boolean status, Boolean offert, Integer price, Integer special_price, Instant createAt,
                                  Instant updateAt){
        if (id == null){
            throw new BusinessExceptions(ServiceMessageExceptions.INVALID_DATA);
        }
        validateInputs(name,description);

        return new Service(
                id,
                barberiaId,
                name,
                description,
                duration,
                status,
                offert,
                price,
                special_price,
                createAt,
                updateAt
        );
    }

    private static void validateInputs(String name, String description) {

        boolean invalid = isNullOrBlank(name)
                || isNullOrBlank(description);

        if (invalid) {
            throw new BusinessExceptions(
                    ServiceMessageExceptions.SERVICE_INVALID
            );
        }
    }

    private static boolean isNullOrBlank(String texto){
        return texto == null || texto.isBlank();
    }

    public Integer getId() { return id; }

    public UUID getBarberiaId() { return barberiaId; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public Integer getDuration() { return duration; }

    public Boolean getStatus() { return status; }

    public Boolean getOffert() { return offert; }

    public Integer getPrice() { return price; }

    public Integer getSpecial_price() { return special_price; }

    public Instant getCreateAt() { return createAt; }

    public Instant getUpdateAt() { return updateAt; }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", barberiaId=" + barberiaId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", duration=" + duration +
                ", status=" + status +
                ", offert=" + offert +
                ", price=" + price +
                ", special_price=" + special_price +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}


