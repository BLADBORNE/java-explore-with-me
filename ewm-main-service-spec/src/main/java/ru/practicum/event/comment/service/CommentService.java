package ru.practicum.event.comment.service;

import ru.practicum.event.comment.dto.NewCommentDto;
import ru.practicum.event.comment.dto.UpdateCommentDto;
import ru.practicum.event.comment.model.Comment;

import java.util.List;

public interface CommentService {
    Comment createNewComment(int userId, int eventId, NewCommentDto newCommentDto);

    Comment updateComment(int userId, int commentId, UpdateCommentDto comment);

    Comment getCommentById(int commentId);

    List<Comment> getAllEventComments(int eventId);

    void deleteCommentById(int userId, int commentId);
}