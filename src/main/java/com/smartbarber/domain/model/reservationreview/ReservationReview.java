package com.smartbarber.domain.model.reservationreview;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.reservationreview.ReservationReviewMessageExceptions;

import java.time.Instant;
import java.util.UUID;

public class ReservationReview {

    private final Integer id;
    private final UUID reservationId;
    private final Integer qualification;
    private final String comment;
    private final Instant createAt;
    private final Instant updateAt;

    private ReservationReview(
            Integer id,
            UUID reservationId,
            Integer qualification,
            String comment,
            Instant createAt,
            Instant updateAt
    ) {
        this.id = id;
        this.reservationId = reservationId;
        this.qualification = qualification;
        this.comment = comment;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public static ReservationReview createReservationReview(
            Integer id,
            UUID reservationId,
            Integer qualification,
            String comment
    ) {
        validateInputs(reservationId, qualification, comment);

        return new ReservationReview(
                id,
                reservationId,
                qualification,
                comment,
                Instant.now(),
                null
        );
    }

    public static ReservationReview update(
            Integer id,
            UUID reservationId,
            Integer qualification,
            String comment,
            Instant createAt
    ) {
        if (id == null || createAt == null) {
            throw new BusinessExceptions(
                    ReservationReviewMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(reservationId, qualification, comment);

        return new ReservationReview(
                id,
                reservationId,
                qualification,
                comment,
                createAt,
                Instant.now()
        );
    }

    public static ReservationReview rebuild(
            Integer id,
            UUID reservationId,
            Integer qualification,
            String comment,
            Instant createAt,
            Instant updateAt
    ) {
        if (id == null || createAt == null) {
            throw new BusinessExceptions(
                    ReservationReviewMessageExceptions.INVALID_DATA
            );
        }

        validateInputs(reservationId, qualification, comment);

        return new ReservationReview(
                id,
                reservationId,
                qualification,
                comment,
                createAt,
                updateAt
        );
    }

    private static void validateInputs(
            UUID reservationId,
            Integer qualification,
            String comment
    ) {
        boolean invalidReservation = reservationId == null;
        boolean invalidQualification = qualification == null
                || qualification < 1
                || qualification > 5;
        boolean invalidComment = comment == null || comment.isBlank();

        if (invalidReservation || invalidQualification || invalidComment) {
            throw new BusinessExceptions(
                    ReservationReviewMessageExceptions.INVALID_DATA
            );
        }
    }

    public Integer getId() {
        return id;
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public Integer getQualification() {
        return qualification;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCreateAt() {
        return createAt;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}