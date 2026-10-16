package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.notification;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import static com.smartbarber.infrastructure.entrypoint.utils.constants.HandlerConstant.NOTIFICATION_SERVICE;

@Configuration
@AllArgsConstructor
public class NotificationRuta {

    private final NotificationHandler notificationHandler;

    @Bean
    public RouterFunction<ServerResponse> notificationRutas() {
        return RouterFunctions.route(
                        RequestPredicates.POST(NOTIFICATION_SERVICE + "/crear-notificacion"),
                        notificationHandler::createNotification)
                .andRoute(
                        RequestPredicates.GET(NOTIFICATION_SERVICE + "/id/{id}"),
                        notificationHandler::findNotificationById)
                .andRoute(
                        RequestPredicates.GET(NOTIFICATION_SERVICE + "/reserva/{reservationId}"),
                        notificationHandler::findNotificationsByReservationId)
                .andRoute(
                        RequestPredicates.GET(NOTIFICATION_SERVICE + "/cliente/{customerId}"),
                        notificationHandler::findNotificationsByCustomerId)
                .andRoute(
                        RequestPredicates.PUT(NOTIFICATION_SERVICE + "/actualizar-notificacion/{id}"),
                        notificationHandler::updateNotification);
    }
}
