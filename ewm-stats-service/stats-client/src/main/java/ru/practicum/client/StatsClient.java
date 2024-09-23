package ru.practicum.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.EndpointHitDto;
import reactor.core.publisher.Mono;
import ru.practicum.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StatsClient {
    private final WebClient webClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Mono<EndpointHitDto> createHit(EndpointHitDto endpointHit) {
        return webClient.post()
                .uri("/hit")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(endpointHit)
                .retrieve()
                .bodyToMono(EndpointHitDto.class);
    }

    public Mono<List<ViewStatsDto>> getHitStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/stats")
                        .queryParam("start", start.format(formatter))
                        .queryParam("end", end.format(formatter))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}