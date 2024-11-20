package ru.practicum.event.request.exception;

public class ParticipationRequestStatusCanNotBeChangedException extends RuntimeException {
    public ParticipationRequestStatusCanNotBeChangedException(String message) {
        super(message);
    }
}