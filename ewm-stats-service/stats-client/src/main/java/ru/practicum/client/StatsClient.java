package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.EndpointHitDto;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StatsClient {
    private final WebClient webClient;

    public Mono<EndpointHitDto> createHit(EndpointHitDto endpointHit) {
        return webClient.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(endpointHit)
                .retrieve()
                .bodyToMono(EndpointHitDto.class);
    }
}