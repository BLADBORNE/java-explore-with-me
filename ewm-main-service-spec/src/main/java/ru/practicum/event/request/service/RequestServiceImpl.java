package ru.practicum.event.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.exception.PermissionDeniedException;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.event.request.exception.*;
import ru.practicum.event.request.mapper.ParticipationRequestMapper;
import ru.practicum.event.request.model.ParticipationRequest;
import ru.practicum.event.request.model.ParticipationRequestStatus;
import ru.practicum.event.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.event.request.model.ParticipationUpdateRequestStatus;
import ru.practicum.event.request.repository.RequestRepository;
import ru.practicum.event.service.EventService;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserService userService;
    private final EventService eventService;
    private final ParticipationRequestMapper requestMapper;

    @Override
    public ParticipationRequest createNewRequest(int userId, int eventId) {
        userService.getUserById(userId);

        Event event = eventService.getEventById(eventId);

        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new EventIsNotPublishedException("Нельзя отправлять заявки на участие в неопубликованном событии");
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new InitiatorCanNotSendRequestException("Создатель события не может отправить запрос на участие в нем");
        }

        if (!event.getParticipantLimit().equals(0)) {
            checkParticipationRequestsNumberIsFull(event);
        }

        int countUserRequests = requestRepository.countUserRequests(userId, eventId);

        if (countUserRequests == 1) {
            throw new RepeatedRequestIsNotAllowedException("Запрос на участие в событии может быть только один");
        }

        ParticipationRequest request = requestMapper.toRequest(userId, eventId, LocalDateTime.now());

        if (!event.getRequestModeration() || event.getParticipantLimit().equals(0)) {
            request.setStatus(ParticipationRequestStatus.CONFIRMED);

            event.setConfirmedRequests(event.getConfirmedRequests() + 1);

            eventRepository.save(event);
        }

        return requestRepository.save(request);
    }

    @Override
    public ParticipationRequest getRequestById(int requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> new NoSuchElementException(String
                .format("Отсутствует запрос с id = %d", requestId)));
    }

    @Override
    public List<ParticipationRequest> getUserRequests(int userId) {
        return requestRepository.findByRequester(userId);
    }

    @Override
    public List<ParticipationRequest> getUserRequestsInEvent(int userId, int eventId) {
        User user = userService.getUserById(userId);

        Event event = eventService.getEventById(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            throw new PermissionDeniedException("Только создатель может просмотреть все запросы на участие в событии");
        }

        return requestRepository.findByEvent(eventId);
    }

    @Override
    public EventRequestStatusUpdateRequest updateRequestStatus(int userId, int eventId,
                                                               EventRequestStatusUpdateRequestDto requestDto) {
        userService.getUserById(userId);

        Event event = eventService.getEventById(eventId);

        checkParticipationRequestsNumberIsFull(event);

        int placesAvailable = event.getParticipantLimit() - event.getConfirmedRequests();

        List<ParticipationRequest> confirmedRequests = new ArrayList<>();
        List<ParticipationRequest> rejectedRequests = new ArrayList<>();
        List<ParticipationRequest> requests = requestRepository.findByIdIn(requestDto.getRequestIds());

        if (requestDto.getStatus().equals(ParticipationUpdateRequestStatus.REJECTED)) {
            for (ParticipationRequest request : requests) {
                checkParticipationRequestStatusIsPending(request);

                request.setStatus(ParticipationRequestStatus.REJECTED);

                rejectedRequests.add(request);
            }

            requestRepository.saveAll(requests);

            return requestMapper.toRequestList(confirmedRequests, rejectedRequests);
        }

        if (requestDto.getStatus().equals(ParticipationUpdateRequestStatus.CONFIRMED)) {
            for (ParticipationRequest request : requests) {
                checkParticipationRequestStatusIsPending(request);

                if (placesAvailable == 0) {
                    request.setStatus(ParticipationRequestStatus.REJECTED);

                    rejectedRequests.add(request);
                } else {
                    request.setStatus(ParticipationRequestStatus.CONFIRMED);

                    confirmedRequests.add(request);

                    placesAvailable--;
                }
            }

            requestRepository.saveAll(requests);

            event.setConfirmedRequests(requestRepository.countConfirmedParticipationRequests(eventId));

            eventRepository.save(event);
        }

        return requestMapper.toRequestList(confirmedRequests, rejectedRequests);
    }

    @Override
    public ParticipationRequest cancelUserRequest(int userId, int requestId) {
        userService.getUserById(userId);

        ParticipationRequest request = getRequestById(requestId);

        if (!request.getRequester().equals(userId)) {
            throw new PermissionDeniedException("Отклонить заявку на участие в событии может только владелец");
        }

        request.setStatus(ParticipationRequestStatus.CANCELED);

        return requestRepository.save(request);
    }

    private void checkParticipationRequestsNumberIsFull(Event event) {
        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ParticipationLimitIsFullException("Достигнут лимит заявок на данное событие");
        }
    }

    private void checkParticipationRequestStatusIsPending(ParticipationRequest request) {
        if (!request.getStatus().equals(ParticipationRequestStatus.PENDING)) {
            throw new ParticipationRequestStatusCanNotBeChangedException("Статус можно изменить только у " +
                    "заявок, находящихся в состоянии ожидания ");
        }
    }
}