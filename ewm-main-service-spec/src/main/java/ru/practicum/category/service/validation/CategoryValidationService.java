package ru.practicum.category.service.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.exeption.CategoryNameAlreadyUsedException;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryValidationService {
    private final CategoryRepository categoryRepository;

    public void checkDuplicateEventNames(String categoryName) {
        Optional<Category> duplicateName = categoryRepository.findByNameEquals(categoryName);

        if (duplicateName.isPresent()) {
            throw new CategoryNameAlreadyUsedException(String.format("Имя %s уже занято другой категорией", categoryName));
        }
    }
}