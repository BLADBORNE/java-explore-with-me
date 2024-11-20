package ru.practicum.event.exception;

public class EventStatusCanNotBeChangedException extends RuntimeException {
    public EventStatusCanNotBeChangedException(String message) {
        super(message);
    }
}