package ru.practicum.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import ru.practicum.user.exception.AlreadyExistException;
import ru.practicum.user.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice(value = "ru.practicum.user.controller")
public class UserErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler({AlreadyExistException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleAlreadyExistException(final Exception e) {
        return new ApiError(HttpStatus.CONFLICT.getReasonPhrase(), "Было нарушено ограничение целостности", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(final WebExchangeBindException e) {
        return new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Запрос составлен неправильно", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }
}