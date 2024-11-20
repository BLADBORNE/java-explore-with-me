package ru.practicum.category.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Slf4j
public class CategoryAdminController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createNewCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Получен запрос на создание новой категории");

        CategoryDto categoryDto = categoryMapper.toDto(categoryService.createNewCategory(newCategoryDto));

        log.info("Успешно создана категория с id : {}", categoryDto.getId());

        return ResponseEntity.status(201).body(categoryDto);
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable(value = "catId") Integer catId,
            @RequestBody @Valid NewCategoryDto updatedCategoryDto
    ) {
        log.info("Получен запрос на обновление имени у категории с id : {}", catId);

        CategoryDto categoryDto = categoryMapper.toDto(categoryService.updateCategory(updatedCategoryDto, catId));

        if (updatedCategoryDto.getName().equals(categoryDto.getName())) {
            log.info("Обновление имени у категории с id : {} не произошло, имена равны", categoryDto.getId());
        } else {
            log.info("Успешно обновлено имя у категории с id : {} на имя: {}", categoryDto.getId(), categoryDto.getName());
        }

        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "catId") Integer catId) {
        log.info("Получен запрос на удаление категории по id = {}", catId);

        categoryService.deleteCategory(catId);

        log.info("Успешно удалена категория с id : {}", catId);

        return ResponseEntity.status(204).build();
    }
}