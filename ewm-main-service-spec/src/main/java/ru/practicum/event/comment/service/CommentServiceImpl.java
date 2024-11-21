package ru.practicum.event.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.comment.dto.NewCommentDto;
import ru.practicum.event.comment.dto.UpdateCommentDto;
import ru.practicum.event.comment.mapper.CommentMapper;
import ru.practicum.event.comment.model.Comment;
import ru.practicum.event.comment.repository.CommentRepository;
import ru.practicum.event.exception.PermissionDeniedException;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.user.model.User;
import ru.practicum.user.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserService userService;
    private final EventService eventService;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Comment createNewComment(int userId, int eventId, NewCommentDto newCommentDto) {
        User user = userService.getUserById(userId);

        Event event = eventService.getEventById(eventId);

        return commentRepository.save(commentMapper.toComment(newCommentDto, event, user, LocalDateTime.now()));
    }

    @Transactional
    @Override
    public Comment updateComment(int userId, int commentId, UpdateCommentDto comment) {
        userService.getUserById(userId);

        Comment getComment = getCommentById(commentId);

        if (!getComment.getInitiator().getId().equals(userId)) {
            throw new PermissionDeniedException("Только создатель может изменять комментарий");
        }

        getComment.setText(comment.getText());

        return commentRepository.save(getComment);
    }

    @Transactional(readOnly = true)
    @Override
    public Comment getCommentById(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException(String
                .format("Отсутствует комментарий с id = %d", commentId)));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllEventComments(int eventId) {
        eventService.getEventById(eventId);

        return commentRepository.findByEventId(eventId);
    }

    @Transactional
    @Override
    public void deleteCommentById(int userId, int commentId) {
        Comment comment = getCommentById(commentId);

        if (!comment.getInitiator().getId().equals(userId)) {
            throw new PermissionDeniedException("Только создатель может удалять комментарий");
        }

        commentRepository.deleteById(commentId);
    }
}