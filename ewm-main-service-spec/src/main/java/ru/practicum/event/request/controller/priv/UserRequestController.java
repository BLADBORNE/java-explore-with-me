package ru.practicum.event.request.controller.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.event.request.dto.ParticipationRequestDto;
import ru.practicum.event.request.mapper.ParticipationRequestMapper;
import ru.practicum.event.request.service.RequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users/{userId}/requests")
@Slf4j
public class UserRequestController {
    private final RequestService requestService;
    private final ParticipationRequestMapper mapper;

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> createNewParticipationRequest(
            @PathVariable(value = "userId") Integer userId,
            @RequestParam(value = "eventId") Integer eventId
    ) {
        log.info("Получен запрос на создание заявки от пользователя с id = {} на участие в событии с id = {}", userId,
                eventId);

        ParticipationRequestDto dto = mapper.toDto(requestService.createNewRequest(userId, eventId));

        log.info("Пользователь с id = {} успешно создал заявку на участие в событии с id = {}", userId, dto.getId());

        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<ParticipationRequestDto>> getUserRequests(@PathVariable(value = "userId") Integer userId) {
        log.info("Получен запрос на отправку всех заявок на участие в событиях от пользователя с id = {}", userId);

        List<ParticipationRequestDto> dto = mapper.toDtoList(requestService.getUserRequests(userId));

        log.info("Успешно отправлены пользователю с id = {} все его участия в событиях", userId);

        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<ParticipationRequestDto> cancelUserRequest(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "requestId") Integer requestId
    ) {
        log.info("Получен запрос на отмену от пользователя с id = {} на участие в событии с id = {}", userId, requestId);

        ParticipationRequestDto dto = mapper.toDto(requestService.cancelUserRequest(userId, requestId));

        log.info("Успешно отменена заявка с id = {} от пользовтеля с id = {}", requestId, userId);

        return ResponseEntity.ok(dto);
    }
}