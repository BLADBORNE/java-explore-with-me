package ru.practicum.event.request.service;

import ru.practicum.event.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.event.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.event.request.model.ParticipationRequest;

import java.util.List;

public interface RequestService {
    ParticipationRequest createNewRequest(int userId, int eventId);

    ParticipationRequest getRequestById(int requestId);

    List<ParticipationRequest> getUserRequests(int userId);

    List<ParticipationRequest> getUserRequestsInEvent(int userId, int eventId);

    EventRequestStatusUpdateRequest updateRequestStatus(int userId, int eventId, EventRequestStatusUpdateRequestDto
            requestDto);

    ParticipationRequest cancelUserRequest(int userId, int requestId);
}