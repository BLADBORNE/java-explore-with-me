package ru.practicum.event.location.exception;

public class DeletionIsNotAllowedException extends RuntimeException {
    public DeletionIsNotAllowedException(String message) {
        super(message);
    }
}