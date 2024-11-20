package ru.practicum.category.service;

import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category createNewCategory(NewCategoryDto newCategoryDto);

    Category getCategoryById(int categoryId);

    List<Category> getCategories(int from, int size);

    Category updateCategory(NewCategoryDto updatedCategory, int categoryId);

    void deleteCategory(int categoryId);
}