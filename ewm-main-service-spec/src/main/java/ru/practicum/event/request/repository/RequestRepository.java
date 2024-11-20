package ru.practicum.event.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.request.model.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Integer> {
    @Query("""
            SELECT COUNT(rq.id)
            FROM ParticipationRequest AS rq
            WHERE rq.requester = :requesterId
              AND rq.event = :eventId
              AND (rq.status = 'PENDING' OR rq.status = 'CONFIRMED')""")
    Integer countUserRequests(int requesterId, int eventId);

    @Query("""
            SELECT COUNT(rq.id)
            FROM ParticipationRequest AS rq
            WHERE rq.event = :eventId
              AND rq.status = 'CONFIRMED'""")
    Integer countConfirmedParticipationRequests(int eventId);

    List<ParticipationRequest> findByRequester(int requesterId);

    List<ParticipationRequest> findByIdIn(List<Integer> requestIds);
}