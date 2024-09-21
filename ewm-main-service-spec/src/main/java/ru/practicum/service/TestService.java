package ru.practicum.service;

import reactor.core.publisher.Mono;
import ru.practicum.dto.EndpointHitDto;

public interface TestService {
    Mono<EndpointHitDto> testEndpointHit();
}
