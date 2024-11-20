package ru.practicum.event.exception;

public class EventCanNotBeUpdatedException extends RuntimeException {
    public EventCanNotBeUpdatedException(String message) {
        super(message);
    }
}