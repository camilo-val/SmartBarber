package com.smartbarber.domain.model.reservation;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservation.ServiceReservationMessageExceptions;

import java.util.UUID;

public class ServiceReservationResponse {
    private final UUID id;
    private final String serviceName;
    private final Integer duration;

    private ServiceReservationResponse(UUID id, String serviceName,
                                       Integer duration) {
        this.id = id;
        this.serviceName = serviceName;
        this.duration = duration;
    }

    public static ServiceReservationResponse rebuild (UUID id, String serviceName,
                                                      Integer duration){
        System.out.println("id: " + id + " serviceName: " + serviceName + " duration: " + duration);

        dataValidate(serviceName);
        return new ServiceReservationResponse(id, serviceName, duration);
    }
    private static void dataValidate(
            String serviceName) {
        if (serviceName == null) {
            throw new BusinessExceptions(ServiceReservationMessageExceptions.INVALID_RESERVATION_DATA);
        }
    }

    public UUID getId() {
        return id;
    }


    public String getServiceName() {
        return serviceName;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "ServiceReservation{" +
                "id=" + id +
                ", serviceName=" + serviceName +
                ", duration=" + duration +
                '}';
    }
}

