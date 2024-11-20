package ru.practicum.event.controller.user.pub;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.EventSortStatus;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
@Slf4j
public class PublicUserController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    private ResponseEntity<List<EventFullDto>> getEvents(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "categories", required = false) List<Integer> categories,
            @RequestParam(value = "paid", required = false) Boolean paid,
            @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime rangeEnd,
            @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(value = "sort", required = false) EventSortStatus sort,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на получение всех событий по публичному endpoint");

        List<EventFullDto> eventFullDto = eventMapper.toEventFullDtoList(eventService.getEvents(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size, request));

        log.info("Успешно отправлены все события по публичному endpoint");

        return ResponseEntity.ok(eventFullDto);
    }

    @GetMapping("/{eventId}")
    private ResponseEntity<EventFullDto> getEventByIdByPublicEndpoint(
            @PathVariable(value = "eventId") Integer eventId,
            HttpServletRequest request
    ) {
        log.info("Получен запрос на получение события с id = {} по публичному endpoint", eventId);

        EventFullDto eventFullDto = eventMapper.eventFullDto(eventService.getEventByIdByPublicEndpoint(eventId, request));

        log.info("Успешно отправлено событие с id = {} по публичному endpoint", eventId);

        return ResponseEntity.ok(eventFullDto);
    }
}