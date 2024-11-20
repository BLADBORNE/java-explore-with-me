package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.mapper.HitsMapper;
import ru.practicum.model.EndpointHit;
import ru.practicum.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;
    private final HitsMapper hitsMapper;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EndpointHitDto> createHit(@RequestBody EndpointHit hit) {
        log.info("Получен запрос на создания события по uri {}", hit.getUri());

        EndpointHitDto mainHit = hitsMapper.toEndpointHitDto(statsService.createNewStats(hit));

        log.info("Успешно создано событие с uri = {}", mainHit.getUri());

        return ResponseEntity.ok(mainHit);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<ViewStatsDto>> getStats(
            @RequestParam(value = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(value = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(value = "uris", required = false) List<String> uris,
            @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique
    ) {
        log.info("Получен запрос на получение событий с парараметрами: start={}, end={}, uris={}", start, end,
                uris == null ? null : uris.toString());

        List<ViewStatsDto> viewStatsDtoList = hitsMapper.toViewStatsDto(statsService.getViewStats(uris, start, end, unique));

        log.info("Упешно отправлены события пользователю");

        return ResponseEntity.ok(viewStatsDtoList);
    }
}