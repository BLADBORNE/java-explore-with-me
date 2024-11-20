package ru.practicum.event.request.exception;

public class RepeatedRequestIsNotAllowedException extends RuntimeException {
    public RepeatedRequestIsNotAllowedException(String message) {
        super(message);
    }
}