package ru.practicum.event.request.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ru.practicum.event.request.model.ParticipationUpdateRequestStatus;

import java.util.List;

@Getter
public class EventRequestStatusUpdateRequestDto {
    @NotEmpty
    private List<Integer> requestIds;
    @NotNull
    private ParticipationUpdateRequestStatus status;
}