package ru.practicum.event.comment.model;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.model.Event;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(nullable = false, name = "user_id")
    @ManyToOne
    private User initiator;
    @JoinColumn(nullable = false, name = "event_id")
    @ManyToOne
    private Event event;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false, name = "published_date")
    private LocalDateTime publishedDate;
}