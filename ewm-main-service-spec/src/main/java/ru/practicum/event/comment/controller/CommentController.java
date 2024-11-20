package ru.practicum.event.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.comment.dto.CommentFullDto;
import ru.practicum.event.comment.dto.NewCommentDto;
import ru.practicum.event.comment.mapper.CommentMapper;
import ru.practicum.event.comment.service.CommentService;

@RestController
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping("/{userId}/{eventId}")
    public ResponseEntity<CommentFullDto> createComment(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "eventId") Integer eventId,
            @RequestBody @Valid NewCommentDto newCommentDto
    ) {
        log.info("Получен запрос на создание комментария событию с id = {} от пользователя с id = {}", eventId, userId);

        CommentFullDto commentFullDto = commentMapper.toCommentFullDto(commentService.createNewComment(userId, eventId,
                newCommentDto));

        log.info("Успешно создан комментарий с id = {}", commentFullDto.getId());

        return ResponseEntity.ok(commentFullDto);
    }
}