package ru.practicum.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HitsMapper {
    EndpointHitDto toEndpointHitDto(EndpointHit hit);

    List<ViewStatsDto> toViewStatsDto(List<ViewStats> viewStats);
}