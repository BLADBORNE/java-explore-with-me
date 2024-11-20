package ru.practicum.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewCategoryDto {
    @NotBlank(message = "Категория не должна быть пустая")
    @Size(max = 50, message = "Длина категории не может быть больше 50 символов")
    private String name;
}