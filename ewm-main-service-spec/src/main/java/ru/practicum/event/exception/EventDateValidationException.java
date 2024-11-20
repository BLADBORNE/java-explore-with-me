package ru.practicum.event.exception;

public class EventDateValidationException extends RuntimeException {
    public EventDateValidationException(String message) {
        super(message);
    }
}