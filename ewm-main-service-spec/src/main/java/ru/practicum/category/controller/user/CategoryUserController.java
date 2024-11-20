package ru.practicum.category.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
@Slf4j
public class CategoryUserController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "catId") Integer catId) {
        log.info("Получен запрос на получение категории по id {}", catId);

        CategoryDto categoryDto = categoryMapper.toDto(categoryService.getCategoryById(catId));

        log.info("Успешно отправлена категория с id: {}", categoryDto.getId());

        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories(
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на получение категорий с параметрами from: {}, size: {}", from, size);

        List<CategoryDto> categoryDto = categoryMapper.toDto(categoryService.getCategories(from, size));

        log.info("Успешно отправлены события по параметрам");

        return ResponseEntity.ok(categoryDto);
    }
}