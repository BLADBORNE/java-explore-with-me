package ru.practicum.event.comment.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.event.comment.dto.CommentFullDto;
import ru.practicum.event.comment.mapper.CommentMapper;
import ru.practicum.event.comment.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class PublicUserCommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{comId}")
    public ResponseEntity<CommentFullDto> getCommentById(@PathVariable(value = "comId") Integer comId) {
        log.info("Получен запрос на получение комментария по id = {}", comId);

        CommentFullDto dto = commentMapper.toCommentFullDto(commentService.getCommentById(comId));

        log.info("Успешно отправлен комментарий с id = {}", comId);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{eventId}/all")
    public ResponseEntity<List<CommentFullDto>> getAllEventComments(@PathVariable(value = "eventId") Integer eventId) {

        return ResponseEntity.ok(commentMapper.dtoList(commentService.getAllEventComments(eventId)));
    }
}