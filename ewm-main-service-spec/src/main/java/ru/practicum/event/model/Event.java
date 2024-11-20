package ru.practicum.event.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.model.Category;
import ru.practicum.event.location.model.Location;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String annotation;
    @Column(nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit;
    @Column(name = "confirmed_requests", nullable = false)
    private Integer confirmedRequests;
    @Transient
    private Integer views;
    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private EventStatus state;
    @Column(nullable = false)
    private Boolean paid;

    public Event() {
        this.views = 0;
        this.confirmedRequests = 0;
        this.participantLimit = 0;
        this.requestModeration = true;
        this.state = EventStatus.PENDING;
        this.paid = false;
    }
}