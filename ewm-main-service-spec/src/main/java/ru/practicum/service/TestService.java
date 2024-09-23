package ru.practicum.service;

import reactor.core.publisher.Mono;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.util.List;

public interface TestService {
    Mono<EndpointHitDto> testEndpointHit();

    Mono<List<ViewStatsDto>> getHitStatsTest();
}