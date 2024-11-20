package ru.practicum.event.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.EventStatus;
import ru.practicum.event.model.admin.UpdateEventByAdminDto;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
@Slf4j
public class AdminEventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @PatchMapping("{eventId}")
    public ResponseEntity<EventFullDto> updateEvent(
            @PathVariable(value = "eventId") Integer eventId,
            @RequestBody @Valid UpdateEventByAdminDto updateEvent
    ) {
        log.info("Получен запрос на обновление события с id = {} от админа", eventId);

        EventFullDto eventFullDto = eventMapper.eventFullDto(eventService.updateEventByAdmin(updateEvent, eventId));

        log.info("Успешно обновлено событие с id = {}", eventId);

        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping
    public ResponseEntity<List<EventFullDto>> getAllEvents(
            @RequestParam(value = "users", required = false) List<Integer> usersId,
            @RequestParam(value = "states", required = false) List<EventStatus> states,
            @RequestParam(value = "categories", required = false) List<Integer> categoriesId,
            @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeEnd,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на получение всех событий с параметрами: usersId {} states {} categoriesId {} " +
                        "rangeStart {} rangeEnd {} from {} size {}", usersId == null ? null : usersId.toString(),
                states == null ? null : states.toString(), categoriesId == null ? null : categoriesId.toString(),
                rangeStart, rangeEnd, from, size);

        List<EventFullDto> dtoList = eventMapper.toEventFullDtoList(eventService.getAllEventsByAdmin(usersId, states,
                categoriesId, rangeStart, rangeEnd, from, size));

        log.info("Упешно отправлены админу события по заданным параметрам");

        return ResponseEntity.ok(dtoList);
    }
}