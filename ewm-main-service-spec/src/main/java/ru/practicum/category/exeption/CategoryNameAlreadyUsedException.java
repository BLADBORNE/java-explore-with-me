package ru.practicum.category.exeption;

public class CategoryNameAlreadyUsedException extends RuntimeException {
    public CategoryNameAlreadyUsedException(String message) {
        super(message);
    }
}