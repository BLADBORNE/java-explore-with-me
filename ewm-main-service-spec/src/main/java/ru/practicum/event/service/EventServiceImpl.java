package ru.practicum.event.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.client.StatsClient;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.exception.*;
import ru.practicum.event.location.model.Location;
import ru.practicum.event.location.service.LocationService;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.mapper.StatsMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.event.dto.UpdateEventByUserDto;
import ru.practicum.event.model.EventSortStatus;
import ru.practicum.event.model.admin.UpdateEventByAdminDto;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.validation.EventDateValidationService;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final EventMapper eventMapper;
    private final EventDateValidationService eventDateValidationService;
    private final StatsClient client;
    private final StatsMapper statsMapper;

    @Transactional
    @Override
    public Event createNewEvent(NewEventDto newEventDto, int initiatorId) {
        eventDateValidationService.checkDateValidation(newEventDto.getEventDate());

        User user = userService.getUserById(initiatorId);

        Category category = categoryService.getCategoryById(newEventDto.getCategory());

        Location location = locationService.createNewLocation(newEventDto.getLocation());

        return eventRepository.save(eventMapper.toEvent(newEventDto, user, category, location,
                LocalDateTime.now()));
    }

    @Override
    public Event getEventById(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NoSuchElementException(String
                .format("Отсутствует событие с id = %d", eventId)));
    }

    @Transactional
    @Override
    public Event getEventById(int userId, int eventId) {
        userService.getUserById(userId);

        Event event = getEventById(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            throw new PermissionDeniedException("Нельзя просмотреть объявление, которое вы не создавали");
        }

        setHitsToEvents(List.of(event));

        return event;
    }

    @Transactional
    @Override
    public Event getEventByIdByPublicEndpoint(int eventId, HttpServletRequest request) {
        Event event = getEventById(eventId);

        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new NoSuchElementException(String.format("Отсутствует событие с id = %d", eventId));
        }

        setHitsToEvents(List.of(event));

        createHit(request);

        return event;
    }

    @Transactional
    @Override
    public Event updateEventByAdmin(UpdateEventByAdminDto updateEventByAdminDto, int eventId) {
        Event event = getEventById(eventId);

        if (updateEventByAdminDto == null) {
            return event;
        }

        eventDateValidationService.checkEventUpdateByAdmin(updateEventByAdminDto, event);

        setHitsToEvents(List.of(event));

        eventRepository.save(event);

        return event;
    }

    @Transactional
    @Override
    public Event updateEventByUser(UpdateEventByUserDto updateEventByUserDto, int userId, int eventId) {
        userService.getUserById(userId);

        Event event = getEventById(eventId);

        if (!event.getInitiator().getId().equals(userId)) {
            throw new PermissionDeniedException("Только создатель может обновлять событие");
        }

        if (updateEventByUserDto == null) {
            return event;
        }

        eventDateValidationService.checkEventUpdateByUser(updateEventByUserDto, event);

        setHitsToEvents(List.of(event));

        eventRepository.save(event);

        return event;
    }

    @Transactional
    @Override
    public List<Event> getUserEvents(int userId, int from, int size) {
        List<Event> events = eventRepository.findByInitiatorId(userId, PageRequest.of(from / size, size));

        setHitsToEvents(events);

        return events;
    }

    @Transactional
    @Override
    public List<Event> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd, Boolean onlyAvailable, EventSortStatus sort, int from,
                                 int size, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(from / size, size);

        checkRangeStartIsNotBeforeRangeEnd(rangeStart, rangeEnd);

        categories = (categories != null && !categories.isEmpty()) ? categories : null;
        rangeStart = rangeStart != null ? rangeStart : LocalDateTime.now();
        rangeEnd = rangeEnd != null ? rangeEnd : LocalDateTime.now().plusYears(100);
        text = text != null ? text.toLowerCase() : "";

        List<Event> events = eventRepository.getAllEventsByUsers(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, pageable);

        createHit(request);

        setHitsToEvents(events);

        return getSortedEvents(events, sort);
    }

    @Transactional
    @Override
    public List<Event> getAllEventsByAdmin(List<Integer> usersId, List<EventStatus> states, List<Integer> categoriesId,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);

        usersId = (usersId != null && !usersId.isEmpty()) ? usersId : null;
        states = (states != null && !states.isEmpty()) ? states : null;
        categoriesId = (categoriesId != null && !categoriesId.isEmpty()) ? categoriesId : null;
        rangeStart = rangeStart != null ? rangeStart : LocalDateTime.now().minusYears(100);
        rangeEnd = rangeEnd != null ? rangeEnd : LocalDateTime.now().plusYears(100);

        List<Event> allEvents = eventRepository.getAllEventsByAdmin(usersId, states, categoriesId, rangeStart, rangeEnd,
                pageable);

        setHitsToEvents(allEvents);

        return allEvents;
    }

    private void setHitsToEvents(List<Event> events) {
        List<ViewStatsDto> dtoList = client.getHitStats(LocalDateTime.now().minusYears(50), LocalDateTime.now()
                        .plusYears(50), events.stream().map(event -> "/events/" + event.getId()).toList(), true)
                .block();

        if (!events.isEmpty() && dtoList != null) {
            events.forEach((event -> setViewsToEvent(event, dtoList)));
        }
    }

    private void setViewsToEvent(Event event, List<ViewStatsDto> result) {
        Optional<ViewStatsDto> views = result
                .stream()
                .filter(stats -> event.getId().equals(Integer.parseInt(stats.getUri().substring(8))))
                .findFirst();

        boolean eventHasViewsAndHasDifferentViewCount = views.isPresent() && !event.getViews().equals(views.get()
                .getHits());
        if (eventHasViewsAndHasDifferentViewCount) {
            event.setViews(views.get().getHits());
        }
    }

    private void createHit(HttpServletRequest request) {
        EndpointHitDto endpointHitDto = statsMapper.toEndpointHitDto(request, LocalDateTime.now());

        client.createHit(endpointHitDto).block();
    }

    private List<Event> getSortedEvents(List<Event> events, EventSortStatus sort) {
        if (sort != null && sort.equals(EventSortStatus.EVENT_DATE)) {
            return events.stream().sorted(Comparator.comparing(Event::getEventDate).reversed()).toList();
        }

        if (sort != null && sort.equals(EventSortStatus.VIEWS)) {
            return events.stream().sorted(Comparator.comparing(Event::getViews)).toList();
        }
        return events;
    }

    private void checkRangeStartIsNotBeforeRangeEnd(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && (rangeEnd.isBefore(rangeStart) || rangeStart.isEqual(rangeEnd))) {
            throw new DateRangeValidationException("Дата начала не может быть раньше даты конца, или даты не могут быть " +
                    "равны");
        }
    }
}