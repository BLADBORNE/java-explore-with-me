package ru.practicum.event.comment.controller.priv;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.practicum.event.comment.dto.CommentFullDto;
import ru.practicum.event.comment.dto.NewCommentDto;
import ru.practicum.event.comment.dto.UpdateCommentDto;
import ru.practicum.event.comment.mapper.CommentMapper;
import ru.practicum.event.comment.service.CommentService;

@RestController
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class PrivateUserController {
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

        return ResponseEntity.status(201).body(commentFullDto);
    }

    @PatchMapping("/{userId}/{eventId}")
    public ResponseEntity<CommentFullDto> updateComment(
            @PathVariable(value = "userId") Integer userId,
            @PathVariable(value = "eventId") Integer eventId,
            @RequestBody @Valid UpdateCommentDto updateCommentDto
    ) {
        log.info("Получен запрос на обновление комментария событию с id = {} от пользователя с id = {}", eventId, userId);

        CommentFullDto commentFullDto = commentMapper.toCommentFullDto(commentService.updateComment(userId, eventId,
                updateCommentDto));

        log.info("Успешно обновлен комментарий с id = {}", commentFullDto.getId());

        return ResponseEntity.ok(commentFullDto);
    }

    @DeleteMapping("/{comId}/{userId}")
    public ResponseEntity<Void> updateComment(
            @PathVariable(value = "comId") Integer comId,
            @PathVariable(value = "userId") Integer userId
    ) {
        log.info("Получен запрос на удаление комментария с id = {}", comId);

        commentService.deleteCommentById(userId, comId);

        log.info("Успешно удален комментарий с id = {}", comId);

        return ResponseEntity.status(204).build();
    }
}