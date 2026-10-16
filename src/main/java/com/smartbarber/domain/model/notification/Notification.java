package com.smartbarber.domain.model.notification;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.notification.NotificationMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class Notification {

    private final UUID id;
    private final UUID customerId;
    private final UUID reservationId;
    private final String title;
    private final String content;
    private final String channelType;
    private final String eventType;
    private final String status;
    private final Integer retries;
    private final String messageError;
    private final Instant scheduledDate;
    private final Instant dateSent;
    private final Instant createAt;

    private Notification(
            UUID id,
            UUID customerId,
            UUID reservationId,
            String title,
            String content,
            String channelType,
            String eventType,
            String status,
            Integer retries,
            String messageError,
            Instant scheduledDate,
            Instant dateSent,
            Instant createAt
    ) {
        this.id = id;
        this.customerId = customerId;
        this.reservationId = reservationId;
        this.title = title;
        this.content = content;
        this.channelType = channelType;
        this.eventType = eventType;
        this.status = status;
        this.retries = retries;
        this.messageError = messageError;
        this.scheduledDate = scheduledDate;
        this.dateSent = dateSent;
        this.createAt = createAt;
    }

    public static Notification createNotification(
            UUID customerId,
            UUID reservationId,
            String title,
            String content,
            String channelType,
            String eventType,
            String status,
            Integer retries,
            String messageError,
            Instant scheduledDate,
            Instant dateSent
    ) {
        validateInputs(
                customerId, title, content, channelType,
                eventType, status, retries
        );

        return new Notification(
                null,
                customerId,
                reservationId,
                title,
                content,
                channelType,
                eventType,
                status,
                retries,
                messageError,
                scheduledDate,
                dateSent,
                Instant.now()
        );
    }

    public Notification updateNotification(
            UUID id,
            UUID customerId,
            UUID reservationId,
            String title,
            String content,
            String channelType,
            String eventType,
            String status,
            Integer retries,
            String messageError,
            Instant scheduledDate,
            Instant dateSent
    ) {
        if (id == null) {
            throw new BusinessExceptions(
                    NotificationMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(
                customerId, title, content, channelType,
                eventType, status, retries
        );

        return new Notification(
                id,
                customerId,
                reservationId,
                title,
                content,
                channelType,
                eventType,
                status,
                retries,
                messageError,
                scheduledDate,
                dateSent,
                this.createAt
        );
    }

    public static Notification rebuild(
            UUID id,
            UUID customerId,
            UUID reservationId,
            String title,
            String content,
            String channelType,
            String eventType,
            String status,
            Integer retries,
            String messageError,
            Instant scheduledDate,
            Instant dateSent,
            Instant createAt
    ) {
        if (id == null || createAt == null) {
            throw new BusinessExceptions(
                    NotificationMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(
                customerId, title, content, channelType,
                eventType, status, retries
        );

        return new Notification(
                id,
                customerId,
                reservationId,
                title,
                content,
                channelType,
                eventType,
                status,
                retries,
                messageError,
                scheduledDate,
                dateSent,
                createAt
        );
    }

    private static void validateInputs(
            UUID customerId,
            String title,
            String content,
            String channelType,
            String eventType,
            String status,
            Integer retries
    ) {
        boolean invalid =
                customerId == null
                        || isNullOrBlank(title)
                        || isNullOrBlank(content)
                        || isNullOrBlank(channelType)
                        || isNullOrBlank(eventType)
                        || isNullOrBlank(status)
                        || retries == null
                        || retries < 0;

        if (invalid) {
            throw new BusinessExceptions(
                    NotificationMessageExceptions.INVALID_DATA
            );
        }
    }

    private static boolean isNullOrBlank(String value) {
        return value == null || value.isBlank();
    }

    public UUID getId() {
        return id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getChannelType() {
        return channelType;
    }

    public String getEventType() {
        return eventType;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRetries() {
        return retries;
    }

    public String getMessageError() {
        return messageError;
    }

    public Instant getScheduledDate() {
        return scheduledDate;
    }

    public Instant getDateSent() {
        return dateSent;
    }

    public Instant getCreateAt() {
        return createAt;
    }
}

    /*@Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", reservaId=" + reservaId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", channelType='" + channelType + '\'' +
                ", eventType='" + eventType + '\'' +
                ", status='" + status + '\'' +
                ", retries=" + retries +
                ", messageError='" + messageError + '\'' +
                ", scheduledDate=" + scheduledDate +
                ", dateSent=" + dateSent +
                ", createAt=" + createAt +
                '}';
    }*/

