package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestServiceImpl implements TestService {
    private final StatsClient statsClient;

    @Override
    public Mono<EndpointHitDto> testEndpointHit() {
        EndpointHitDto endpointHit = new EndpointHitDto();

        endpointHit.setApp("Main");
        endpointHit.setIp("1");
        endpointHit.setUri("/test");
        endpointHit.setTimestamp(LocalDateTime.now());

        return statsClient.createHit(endpointHit);
    }

    @Override
    public Mono<List<ViewStatsDto>> getHitStatsTest() {
        return statsClient.getHitStats(LocalDateTime.now().minusHours(2), LocalDateTime.now(), List.of("/test"), null);
    }
}