package ru.practicum.event.location.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.location.dto.LocationDto;
import ru.practicum.event.location.mapper.LocationMapper;
import ru.practicum.event.location.model.Location;
import ru.practicum.event.location.repository.LocationRepository;
import ru.practicum.event.repository.EventRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final EventRepository eventRepository;

    @Override
    public Location createNewLocation(LocationDto locationDto) {
        log.info("Получен запрос на создание новой локации");

        Location location = locationRepository.save(locationMapper.toLocation(locationDto));

        log.info("Успешно создана локация с id = {}", location.getId());

        return location;
    }

    @Override
    public void deleteLocationById(int locationId) {
        log.info("Получен запрос на удаление локации с id = {}", locationId);

        Integer countLocationsById = eventRepository.countByLocationId(locationId);

        if (countLocationsById < 2) {
            locationRepository.deleteById(locationId);

            log.info("Успешно удалена локация с id = {}", locationId);
            return;
        }

        log.warn("Локацию нельзя удалить, тк она используется в двух и более событиях");
    }
}