package ru.practicum.event.model.admin;

import lombok.Getter;
import ru.practicum.event.model.AbstractUpdateEvent;

@Getter
public class UpdateEventByAdminDto extends AbstractUpdateEvent {
    private EventApprovalStatus stateAction;
}