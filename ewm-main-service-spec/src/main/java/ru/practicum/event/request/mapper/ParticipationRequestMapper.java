package ru.practicum.event.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.event.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.event.request.dto.ParticipationRequestDto;
import ru.practicum.event.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.event.request.model.ParticipationRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ParticipationRequestMapper {
    public ParticipationRequestDto toDto(ParticipationRequest participationRequest) {
        ParticipationRequestDto dto = new ParticipationRequestDto();

        dto.setId(participationRequest.getId());
        dto.setRequester(participationRequest.getRequester());
        dto.setEvent(participationRequest.getEvent());
        dto.setCreated(participationRequest.getCreated());
        dto.setStatus(participationRequest.getStatus());

        return dto;
    }

    public List<ParticipationRequestDto> toDtoList(List<ParticipationRequest> participationRequests) {
        return participationRequests.stream().map(this::toDto).collect(Collectors.toList());
    }

    public ParticipationRequest toRequest(int requesterId, int eventId, LocalDateTime created) {
        ParticipationRequest participationRequest = new ParticipationRequest();

        participationRequest.setRequester(requesterId);
        participationRequest.setEvent(eventId);
        participationRequest.setCreated(created);

        return participationRequest;
    }

    public EventRequestStatusUpdateRequest toRequestList(List<ParticipationRequest> confirmedRequests,
                                                         List<ParticipationRequest> rejectedRequests) {
        EventRequestStatusUpdateRequest result = new EventRequestStatusUpdateRequest();

        if (confirmedRequests != null) {
            result.setConfirmedRequests(toDtoList(confirmedRequests));
        }

        if (rejectedRequests != null) {
            result.setRejectedRequests(toDtoList(rejectedRequests));
        }

        return result;
    }

    public EventRequestStatusUpdateResultDto toRequestDtoList(EventRequestStatusUpdateRequest updateRequest) {
        EventRequestStatusUpdateResultDto requestDto = new EventRequestStatusUpdateResultDto();

        requestDto.setConfirmedRequests(updateRequest.getConfirmedRequests());
        requestDto.setRejectedRequests(updateRequest.getRejectedRequests());

        return requestDto;
    }
}