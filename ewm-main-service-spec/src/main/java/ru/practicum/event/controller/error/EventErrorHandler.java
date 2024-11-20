package ru.practicum.event.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import ru.practicum.event.exception.*;
import ru.practicum.event.request.exception.ParticipationLimitIsFullException;
import ru.practicum.event.request.exception.ParticipationRequestStatusCanNotBeChangedException;
import ru.practicum.user.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(value = "ru.practicum.event.controller")
public class EventErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({DateRangeValidationException.class, WebExchangeBindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintValidationException(final Exception e) {
        return new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Запрос составлен неправильно", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoSuchElementException(final NoSuchElementException e) {
        return new ApiError(HttpStatus.NOT_FOUND.getReasonPhrase(), "Запрашиваемый объект не был найден",
                e.getMessage(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler({EventDateValidationException.class, EventStatusCanNotBeChangedException.class, PermissionDeniedException.class,
            EventCanNotBeUpdatedException.class, ParticipationRequestStatusCanNotBeChangedException.class,
            ParticipationLimitIsFullException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final Exception e) {
        return new ApiError(HttpStatus.CONFLICT.getReasonPhrase(), "Было нарушено ограничение целостности", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler({StatsServerUnavailableException.class, StatsServerLogicException.class})
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ApiError handleBadGateWay(final Exception e) {
        return new ApiError(HttpStatus.BAD_GATEWAY.getReasonPhrase(), "Произошла ошибка на стороне другого сервиса", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }
}