package ru.practicum.event.mapper;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import ru.practicum.dto.EndpointHitDto;

import java.time.LocalDateTime;

@Component
public class StatsMapper {
    private static final String APP = "ewm-main-service";

    public EndpointHitDto toEndpointHitDto(HttpServletRequest request, LocalDateTime timestamp) {
        EndpointHitDto endpointHitDto = new EndpointHitDto();

        endpointHitDto.setApp(APP);
        endpointHitDto.setUri(request.getRequestURI());
        endpointHitDto.setIp(request.getRemoteAddr());
        endpointHitDto.setTimestamp(timestamp);

        return endpointHitDto;
    }
}