package ru.practicum.event.dto;

import ru.practicum.event.comment.dto.CommentFullDto;

import java.util.List;

public class EventFullDtoWithComments extends EventFullDto {
    private List<CommentFullDto> comment;
}