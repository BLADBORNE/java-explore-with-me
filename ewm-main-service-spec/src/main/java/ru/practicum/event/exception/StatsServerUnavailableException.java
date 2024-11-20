package ru.practicum.event.exception;

public class StatsServerUnavailableException extends RuntimeException {
    public StatsServerUnavailableException(String message) {
        super(message);
    }
}