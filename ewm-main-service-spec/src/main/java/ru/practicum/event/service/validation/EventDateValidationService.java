package ru.practicum.event.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.service.CategoryService;
import ru.practicum.event.exception.EventCanNotBeUpdatedException;
import ru.practicum.event.exception.EventDateValidationException;
import ru.practicum.event.exception.EventStatusCanNotBeChanged;
import ru.practicum.event.location.mapper.LocationMapper;
import ru.practicum.event.location.service.LocationService;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;
import ru.practicum.event.dto.UpdateEventByUserDto;
import ru.practicum.event.model.admin.EventApprovalStatus;
import ru.practicum.event.model.admin.UpdateEventByAdminDto;
import ru.practicum.event.model.UserEventReview;
import ru.practicum.event.model.AbstractUpdateEvent;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class EventDateValidationService {
    private final CategoryService categoryService;
    private final LocationService locationService;
    private final LocationMapper locationMapper;

    public void checkDateValidation(LocalDateTime eventDate) {
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new EventDateValidationException("Дата события должна быть на 2 час позже от текущего времени");
        }
    }

    public void checkEventUpdateByUser(UpdateEventByUserDto updateEventByUserDto, Event event) {
        if (event.getState().equals(EventStatus.PUBLISHED)) {
            throw new EventCanNotBeUpdatedException("Редактировать можно только отмененные или находящиеся на модерации события");
        }

        setUpdatedValuesIfEventsFieldsIsNotNull(updateEventByUserDto, event);

        if (updateEventByUserDto.getStateAction() != null) {
            if (event.getState().equals(EventStatus.CANCELED) && updateEventByUserDto.getStateAction()
                    .equals(UserEventReview.SEND_TO_REVIEW)) {
                event.setState(EventStatus.PENDING);
                return;
            }

            if (event.getState().equals(EventStatus.PENDING) && updateEventByUserDto.getStateAction()
                    .equals(UserEventReview.CANCEL_REVIEW)) {
                event.setState(EventStatus.CANCELED);
                return;
            }

            String errorMessage = getErrorMessage(updateEventByUserDto, event);

            throw new EventStatusCanNotBeChanged(errorMessage);
        }
    }

    public LocalDateTime checkDateValidationByAdmin(LocalDateTime updatedEventDate, LocalDateTime
            approvedEventDate) {
        if (updatedEventDate != null && updatedEventDate.isBefore(approvedEventDate.plusHours(1))) {
            throw new EventDateValidationException("Измененная дата события должна начинаться не ранее, чем за час" +
                    "после публикации");
        }

        return approvedEventDate;
    }


    public void checkEventUpdateByAdmin(UpdateEventByAdminDto updateEventByAdminDto, Event event) {
        setUpdatedValuesIfEventsFieldsIsNotNull(updateEventByAdminDto, event);

        if (updateEventByAdminDto.getStateAction() != null) {
            if (updateEventByAdminDto.getStateAction().equals(EventApprovalStatus.PUBLISH_EVENT) && event.getState()
                    .equals(EventStatus.PENDING)) {
                event.setState(EventStatus.PUBLISHED);
                event.setPublishedOn(checkDateValidationByAdmin(updateEventByAdminDto.getEventDate(), LocalDateTime.now()));

                return;
            }

            if (updateEventByAdminDto.getStateAction().equals(EventApprovalStatus.REJECT_EVENT) && event.getState()
                    .equals(EventStatus.PENDING)) {
                event.setState(EventStatus.CANCELED);

                return;
            }

            String errorMessage = getErrorMessage(updateEventByAdminDto, event);

            throw new EventStatusCanNotBeChanged(errorMessage);
        }
    }

    private void setUpdatedValuesIfEventsFieldsIsNotNull(AbstractUpdateEvent abstractUpdateEvent, Event event) {
        if (abstractUpdateEvent.getTitle() != null && !abstractUpdateEvent.getTitle().equals(event.getTitle())) {
            event.setTitle(abstractUpdateEvent.getTitle());
        }

        if (abstractUpdateEvent.getAnnotation() != null && !abstractUpdateEvent.getAnnotation().equals(event
                .getAnnotation())) {
            event.setAnnotation(abstractUpdateEvent.getAnnotation());
        }

        if (abstractUpdateEvent.getDescription() != null && !abstractUpdateEvent.getDescription().equals(event
                .getDescription())) {
            event.setDescription(abstractUpdateEvent.getDescription());
        }

        if (abstractUpdateEvent.getCategory() != null && !abstractUpdateEvent.getCategory().equals(event
                .getCategory().getId())) {
            event.setCategory(categoryService.getCategoryById(abstractUpdateEvent.getCategory()));
        }

        if (abstractUpdateEvent.getLocation() != null && !abstractUpdateEvent.getLocation().equals(locationMapper
                .toLocationDto(event.getLocation()))) {
            locationService.deleteLocationById(event.getLocation().getId());

            event.setLocation(locationService.createNewLocation(abstractUpdateEvent.getLocation()));
        }

        if (abstractUpdateEvent.getEventDate() != null && !abstractUpdateEvent.getEventDate().equals(event
                .getEventDate())) {
            if (abstractUpdateEvent instanceof UpdateEventByUserDto) {
                checkDateValidation(abstractUpdateEvent.getEventDate());
            }

            event.setEventDate(abstractUpdateEvent.getEventDate());
        }

        if (abstractUpdateEvent.getParticipantLimit() != null && !abstractUpdateEvent.getParticipantLimit()
                .equals(event.getParticipantLimit())) {
            event.setParticipantLimit(abstractUpdateEvent.getParticipantLimit());
        }

        if (abstractUpdateEvent.getRequestModeration() != null && !abstractUpdateEvent.getRequestModeration()
                .equals(event.getRequestModeration())) {
            event.setRequestModeration(abstractUpdateEvent.getRequestModeration());
        }

        if (abstractUpdateEvent.getPaid() != null && !abstractUpdateEvent.getPaid().equals(event.getPaid())) {
            event.setPaid(abstractUpdateEvent.getPaid());
        }
    }

    private String getErrorMessage(UpdateEventByAdminDto updateEventByAdminDto, Event event) {
        String errorMessage = "";

        if (event.getState().equals(EventStatus.PUBLISHED) && updateEventByAdminDto.getStateAction()
                .equals(EventApprovalStatus.PUBLISH_EVENT)) {
            errorMessage = "Публикация уже опубликованного события запрещена";
        }

        if (event.getState().equals(EventStatus.PUBLISHED) && updateEventByAdminDto.getStateAction()
                .equals(EventApprovalStatus.REJECT_EVENT)) {
            errorMessage = "Нельзя отменить событие после его публикации";
        }

        if (event.getState().equals(EventStatus.CANCELED) && updateEventByAdminDto.getStateAction()
                .equals(EventApprovalStatus.REJECT_EVENT)) {
            errorMessage = "Отмена уже отмененного события запрещена";
        }

        if (event.getState().equals(EventStatus.CANCELED) && updateEventByAdminDto.getStateAction()
                .equals(EventApprovalStatus.PUBLISH_EVENT)) {
            errorMessage = "Публикация уже отмененного события запрещена";
        }

        return errorMessage;
    }

    private String getErrorMessage(UpdateEventByUserDto updateEventByUserDto, Event event) {
        String errorMessage = "";

        if (event.getState().equals(EventStatus.PENDING) && updateEventByUserDto.getStateAction()
                .equals(UserEventReview.SEND_TO_REVIEW)) {
            errorMessage = "Нельзя повторно отправить событие на модерацию, находящееся в этом же статусе";
        }

        if (event.getState().equals(EventStatus.CANCELED) && updateEventByUserDto.getStateAction()
                .equals(UserEventReview.CANCEL_REVIEW)) {
            errorMessage = "Отмена уже отмененного события запрещена";
        }

        return errorMessage;
    }
}