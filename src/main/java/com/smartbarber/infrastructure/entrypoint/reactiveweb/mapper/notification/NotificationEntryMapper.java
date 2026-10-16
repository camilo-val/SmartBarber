package com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.notification;

import com.smartbarber.application.command.in.notification.NotificationCommand;
import com.smartbarber.domain.model.notification.Notification;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.notification.NotificationRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.notification.NotificationRsDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationEntryMapper {

    NotificationCommand toCommand(NotificationRqDto request);

    NotificationRsDto toResponse(Notification notification);
}

