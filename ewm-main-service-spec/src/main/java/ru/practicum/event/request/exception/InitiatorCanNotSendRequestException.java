package ru.practicum.event.request.exception;

public class InitiatorCanNotSendRequestException extends RuntimeException {
    public InitiatorCanNotSendRequestException(String message) {
        super(message);
    }
}