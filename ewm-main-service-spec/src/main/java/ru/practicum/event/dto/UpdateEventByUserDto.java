package ru.practicum.event.dto;

import lombok.Getter;
import ru.practicum.event.model.AbstractUpdateEvent;
import ru.practicum.event.model.UserEventReview;

@Getter
public class UpdateEventByUserDto extends AbstractUpdateEvent {
    private UserEventReview stateAction;
}