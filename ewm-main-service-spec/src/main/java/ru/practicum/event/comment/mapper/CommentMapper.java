package ru.practicum.event.comment.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.event.comment.dto.CommentFullDto;
import ru.practicum.event.comment.dto.NewCommentDto;
import ru.practicum.event.comment.model.Comment;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public CommentFullDto toCommentFullDto(Comment comment) {
        CommentFullDto commentFullDto = new CommentFullDto();

        commentFullDto.setId(comment.getId());
        commentFullDto.setEvent(comment.getEvent().getId());
        commentFullDto.setInitiator(comment.getInitiator().getId());
        commentFullDto.setText(comment.getText());
        commentFullDto.setPublishedDate(comment.getPublishedDate());

        return commentFullDto;
    }

    public Comment toComment(NewCommentDto newCommentDto, Event event, User user, LocalDateTime created) {
        Comment comment = new Comment();

        comment.setInitiator(user);
        comment.setEvent(event);
        comment.setPublishedDate(created);
        comment.setText(newCommentDto.getText());

        return comment;
    }
}