package ru.practicum.event.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.location.mapper.LocationMapper;
import ru.practicum.event.location.model.Location;
import ru.practicum.event.model.Event;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class EventMapper {
    private final CategoryMapper categoryMapper;
    private final LocationMapper locationMapper;
    private final UserMapper userMapper;

    public Event toEvent(NewEventDto newEventDto, User user, Category category,
                         Location location, LocalDateTime eventTime) {

        Event event = new Event();

        event.setTitle(newEventDto.getTitle());
        event.setAnnotation(newEventDto.getAnnotation());
        event.setCategory(category);
        event.setInitiator(user);
        event.setLocation(location);
        event.setEventDate(newEventDto.getEventDate());
        event.setDescription(newEventDto.getDescription());

        if (newEventDto.getParticipantLimit() != null) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        }

        if (newEventDto.getRequestModeration() != null) {
            event.setRequestModeration(newEventDto.getRequestModeration());
        }

        if (newEventDto.getPaid() != null) {
            event.setPaid(newEventDto.getPaid());
        }

        event.setCreatedOn(eventTime);

        return event;
    }

    public EventFullDto eventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto();

        eventFullDto.setId(event.getId());
        eventFullDto.setTitle(event.getTitle());
        eventFullDto.setAnnotation(event.getAnnotation());
        eventFullDto.setDescription(event.getDescription());
        eventFullDto.setCategory(categoryMapper.toDto(event.getCategory()));
        eventFullDto.setInitiator(userMapper.toUserShortDto(event.getInitiator()));
        eventFullDto.setLocation(locationMapper.toLocationDto(event.getLocation()));
        eventFullDto.setCreatedOn(event.getCreatedOn());
        eventFullDto.setPublishedOn(event.getPublishedOn());
        eventFullDto.setEventDate(event.getEventDate());
        eventFullDto.setParticipantLimit(event.getParticipantLimit());
        eventFullDto.setConfirmedRequests(event.getConfirmedRequests());
        eventFullDto.setViews(event.getViews());
        eventFullDto.setRequestModeration(event.getRequestModeration());
        eventFullDto.setState(event.getState());
        eventFullDto.setPaid(event.getPaid());

        return eventFullDto;
    }

    public List<EventFullDto> toEventFullDtoList(List<Event> events) {
        return events.stream().map(this::eventFullDto).collect(Collectors.toList());
    }

    public EventShortDto toEventShortDto(Event event) {
        EventShortDto eventShortDto = new EventShortDto();

        eventShortDto.setId(event.getId());
        eventShortDto.setTitle(event.getTitle());
        eventShortDto.setAnnotation(event.getAnnotation());
        eventShortDto.setCategory(categoryMapper.toDto(event.getCategory()));
        eventShortDto.setInitiator(userMapper.toUserShortDto(event.getInitiator()));
        eventShortDto.setEventDate(event.getEventDate());
        eventShortDto.setConfirmedRequests(event.getConfirmedRequests());
        eventShortDto.setViews(event.getViews());
        eventShortDto.setPaid(event.getPaid());

        return eventShortDto;
    }

    public List<EventShortDto> eventShortDtoList(List<Event> events) {
        return events.stream().map(this::toEventShortDto).collect(Collectors.toList());
    }
}