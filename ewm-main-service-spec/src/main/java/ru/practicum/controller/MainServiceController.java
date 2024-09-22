package ru.practicum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.TestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainServiceController {
    private final TestService testService;

    @PostMapping("/hit")
    public Mono<EndpointHitDto> createHitTest() {
        return testService.testEndpointHit();
    }

    @GetMapping("/stats")
    public Mono<List<ViewStatsDto>> getHitTest() {
        return testService.getHitStatsTest();
    }
}