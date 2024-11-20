package ru.practicum.event.exception;

public class DateRangeValidationException extends RuntimeException {
    public DateRangeValidationException(String message) {
        super(message);
    }
}