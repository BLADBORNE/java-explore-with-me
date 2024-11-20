package ru.practicum.event.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateCompilationRequestDto {
    private List<Integer> events;
    private Boolean pinned;
    @Size(min = 1, max = 50, message = "Заголовок подборки не может содеражать меньше одного символа и не более 50")
    private String title;
}