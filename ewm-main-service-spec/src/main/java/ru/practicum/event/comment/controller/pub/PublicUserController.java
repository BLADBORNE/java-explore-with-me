package ru.practicum.event.comment.controller.pub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import ru.practicum.event.comment.dto.CommentFullDto;
import ru.practicum.event.comment.mapper.CommentMapper;
import ru.practicum.event.comment.service.CommentService;

@RestController
@RequestMapping("/comments")
@Slf4j
@RequiredArgsConstructor
public class PublicUserController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping("/{comId}")
    public ResponseEntity<CommentFullDto> getCommentById() { }
}
