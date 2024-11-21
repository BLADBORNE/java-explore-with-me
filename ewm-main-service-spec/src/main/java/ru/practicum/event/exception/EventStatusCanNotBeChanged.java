package ru.practicum.event.exception;

public class EventStatusCanNotBeChanged extends RuntimeException {
    public EventStatusCanNotBeChanged(String message) {
        super(message);
    }
}