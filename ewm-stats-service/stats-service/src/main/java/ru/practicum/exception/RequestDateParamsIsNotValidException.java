package ru.practicum.exception;

public class RequestDateParamsIsNotValidException extends RuntimeException {
    public RequestDateParamsIsNotValidException(String message) {
        super(message);
    }
}