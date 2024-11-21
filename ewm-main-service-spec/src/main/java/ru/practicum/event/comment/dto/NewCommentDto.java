package ru.practicum.event.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewCommentDto {
    @NotBlank(message = "Поле не может быть пустым")
    @Size(max = 2000)
    private String text;
}