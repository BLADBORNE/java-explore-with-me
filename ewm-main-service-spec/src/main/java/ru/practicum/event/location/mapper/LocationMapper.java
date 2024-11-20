package ru.practicum.event.location.mapper;

import org.mapstruct.Mapper;
import ru.practicum.event.location.dto.LocationDto;
import ru.practicum.event.location.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    Location toLocation(LocationDto locationDto);

    LocationDto toLocationDto(Location location);
}