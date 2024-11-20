package ru.practicum.event.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByEventId(Integer eventId);
}