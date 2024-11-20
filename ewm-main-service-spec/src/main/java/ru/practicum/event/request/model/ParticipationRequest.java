package ru.practicum.event.request.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events_requests")
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "requester_id", nullable = false)
    private Integer requester;
    @Column(name = "event_id", nullable = false)
    private Integer event;
    @Column(nullable = false)
    private LocalDateTime created;
    @Enumerated(EnumType.STRING)
    private ParticipationRequestStatus status = ParticipationRequestStatus.PENDING;
}