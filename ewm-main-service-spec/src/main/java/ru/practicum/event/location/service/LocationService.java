package ru.practicum.event.location.service;

import ru.practicum.event.location.dto.LocationDto;
import ru.practicum.event.location.model.Location;

public interface LocationService {
    Location createNewLocation(LocationDto locationDto);

    void deleteLocationById(int locationId);
}