package ru.practicum.event.comment.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import ru.practicum.event.exception.PermissionDeniedException;
import ru.practicum.user.model.ApiError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@RestControllerAdvice(value = "ru.practicum.event.comment.controller")
public class CommentErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintValidationException(final WebExchangeBindException e) {
        return new ApiError(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Запрос составлен неправильно", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNoSuchElementException(final NoSuchElementException e) {
        return new ApiError(HttpStatus.NOT_FOUND.getReasonPhrase(), "Запрашиваемый объект не был найден",
                e.getMessage(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final PermissionDeniedException e) {
        return new ApiError(HttpStatus.CONFLICT.getReasonPhrase(), "Было нарушено ограничение целостности", e.getMessage(),
                LocalDateTime.now().format(formatter));
    }
}