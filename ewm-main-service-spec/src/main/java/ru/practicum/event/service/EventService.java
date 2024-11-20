package ru.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.event.dto.UpdateEventByUserDto;
import ru.practicum.event.model.EventSortStatus;
import ru.practicum.event.model.admin.UpdateEventByAdminDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event createNewEvent(NewEventDto newEventDto, int initiatorId);

    Event getEventById(int eventId);

    Event getEventById(int userId, int eventId);

    Event getEventByIdByPublicEndpoint(int eventId, HttpServletRequest request);

    Event updateEventByAdmin(UpdateEventByAdminDto updateEventByAdminDto, int eventId);

    Event updateEventByUser(UpdateEventByUserDto updateEventByUserDto, int userId, int eventI);

    List<Event> getUserEvents(int userId, int from, int size);

    List<Event> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime
            rangeEnd, Boolean onlyAvailable, EventSortStatus sort, int from, int size, HttpServletRequest request);

    List<Event> getAllEventsByAdmin(List<Integer> usersId, List<EventStatus> states, List<Integer> categoriesId,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from,
                                    Integer size);
}