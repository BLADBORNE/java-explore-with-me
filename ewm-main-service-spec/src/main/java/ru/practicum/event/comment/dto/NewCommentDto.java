package ru.practicum.event.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewCommentDto {
    @NotBlank
    @Size(max = 2000)
    private String text;
}