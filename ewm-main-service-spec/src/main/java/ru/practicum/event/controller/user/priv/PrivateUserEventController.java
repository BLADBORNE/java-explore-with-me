package ru.practicum.event.controller.user.priv;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.dto.UpdateEventByUserDto;
import ru.practicum.event.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.event.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.event.request.dto.ParticipationRequestDto;
import ru.practicum.event.request.mapper.ParticipationRequestMapper;
import ru.practicum.event.request.service.RequestService;
import ru.practicum.event.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
@Slf4j
public class PrivateUserEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;
    private final RequestService requestService;
    private final ParticipationRequestMapper requestMapper;

    @PostMapping
    public ResponseEntity<EventFullDto> creteNewEvent(
            @PathVariable("userId") Integer userId,
            @RequestBody @Valid NewEventDto newEventDto
    ) {
        log.info("Получен запрос на создание нового события");

        EventFullDto eventFullDto = eventMapper.eventFullDto(eventService.createNewEvent(newEventDto, userId));

        log.info("Успешно создано событие с id = {}", eventFullDto.getId());

        return ResponseEntity.status(201).body(eventFullDto);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventFullDto> updateEventByUser(
            @PathVariable("userId") Integer userId,
            @PathVariable(value = "eventId") Integer eventId,
            @RequestBody @Valid UpdateEventByUserDto updatedEvent
    ) {
        log.info("Получен запрос на обновление события с id = {} от пользователя с id = {}", eventId, userId);

        EventFullDto eventFullDto = eventMapper.eventFullDto(eventService.updateEventByUser(updatedEvent, userId, eventId));

        log.info("Успешно обновлено событие с id = {}", eventId);

        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> getEventById(
            @PathVariable("userId") Integer userId,
            @PathVariable(value = "eventId") Integer eventId
    ) {
        log.info("Получен запрос на получения ссобытия с id = {} от пользователя с id = {}", eventId, userId);

        EventFullDto eventFullDto = eventMapper.eventFullDto(eventService.getEventById(userId, eventId));

        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getUserEvents(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на отправку всех событий пользователю с id = {} с параметрами: from: {}, size: {}",
                userId, from, size);

        List<EventFullDto> eventFullDto = eventMapper.toEventFullDtoList(eventService.getUserEvents(userId, from, size));

        log.info("Успешно отправлены пользовтаелю с id = {} всего его события", userId);

        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<ParticipationRequestDto>> getUserRequestsInEvent(
            @PathVariable("userId") Integer userId,
            @PathVariable(value = "eventId") Integer eventId
    ) {
        log.info("Получен запрос на просмотр всех заявок на участие в событии с id = {} от ользователя с id = {}",
                eventId, userId);

        List<ParticipationRequestDto> resultDto = requestMapper.toDtoList(requestService.getUserRequestsInEvent(userId,
                eventId));

        log.info("Успешно отправлены все заявки на участие в событии с id = {} от ользователя с id = {}", eventId, userId);

        return ResponseEntity.ok(resultDto);
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResultDto> updateRequestStatus(
            @PathVariable("userId") Integer userId,
            @PathVariable(value = "eventId") Integer eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequestDto requestDto
    ) {
        log.info("Получен запрос на изменение статуса заявок с id: {} пользователя с id = {} на участие в событии " +
                "с id = {}", requestDto.getRequestIds().toString(), userId, eventId);

        EventRequestStatusUpdateResultDto resultDto = requestMapper.toRequestDtoList(requestService
                .updateRequestStatus(userId, eventId, requestDto));

        log.info("Успешно обновлены статусы заявок с id = {} пользователя с id = {} на участие в событии с id = {}",
                requestDto.getRequestIds().toString(), userId, eventId);

        return ResponseEntity.ok(resultDto);
    }
}