package ru.practicum.event.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewCompilationDto {
    private List<Integer> events;
    private Boolean pinned;
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 1, max = 50, message = "Заголовок подборки не может содеражать меньше одного символа и не более 50")
    private String title;
}