package ru.practicum.event.request.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.request.dto.ParticipationRequestDto;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}
