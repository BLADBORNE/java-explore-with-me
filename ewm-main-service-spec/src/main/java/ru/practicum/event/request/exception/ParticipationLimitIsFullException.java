package ru.practicum.event.request.exception;

public class ParticipationLimitIsFullException extends RuntimeException {
    public ParticipationLimitIsFullException(String message) {
        super(message);
    }
}