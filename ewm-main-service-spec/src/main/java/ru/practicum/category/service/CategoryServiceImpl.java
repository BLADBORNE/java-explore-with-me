package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.exeption.CategoryNotEmptyException;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.validation.CategoryValidationService;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidationService categoryValidationService;

    @Override
    public Category createNewCategory(NewCategoryDto newCategoryDto) {
        categoryValidationService.checkDuplicateEventNames(newCategoryDto.getName());

        return categoryRepository.save(categoryMapper.toCategory(newCategoryDto));
    }

    @Override
    public Category getCategoryById(int categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isPresent()) {
            return category.get();
        }

        throw new NoSuchElementException(String.format("Категория с id = '%d' не найдена", categoryId));
    }

    @Override
    public List<Category> getCategories(int from, int size) {
        return categoryRepository.findCategoriesBy(PageRequest.of(from / size, size));
    }

    @Override
    public Category updateCategory(NewCategoryDto updatedCategory, int categoryId) {
        Category getCategory = getCategoryById(categoryId);

        if (getCategory.getName().equals(updatedCategory.getName())) {
            return getCategory;
        }

        categoryValidationService.checkDuplicateEventNames(updatedCategory.getName());

        getCategory.setName(updatedCategory.getName());

        return categoryRepository.save(getCategory);
    }

    @Override
    public void deleteCategory(int categoryId) {
        getCategoryById(categoryId);

        Optional<Event> event = eventRepository.findFirstByCategoryId(categoryId);

        if (event.isPresent()) {
            throw new CategoryNotEmptyException("Категория не может быть удалена, тк она не пуста");
        }

        categoryRepository.deleteById(categoryId);
    }
}