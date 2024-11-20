package ru.practicum.category.exeption;

public class CategoryNotEmptyException extends RuntimeException {
    public CategoryNotEmptyException(String message) {
        super(message);
    }
}