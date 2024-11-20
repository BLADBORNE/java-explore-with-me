package ru.practicum.event.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findFirstByCategoryId(int categoryId);

    @Query("SELECT COUNT(ev.id) " +
            "FROM Event AS ev " +
            "WHERE ev.location.id = :locationId")
    Integer countByLocationId(int locationId);

    List<Event> findByInitiatorId(int initiatorId, Pageable pageable);

    @Query("""
            SELECT ev
            FROM Event AS ev
            WHERE (:usersId IS NULL
                   OR ev.initiator.id IN (:usersId))
              AND (:states IS NULL
                   OR ev.state IN (:states))
              AND (:categoriesId IS NULL
                   OR ev.category.id IN (:categoriesId))
              AND ev.eventDate BETWEEN :rangeStart AND :rangeEnd
            """)
    List<Event> getAllEventsByAdmin(List<Integer> usersId, List<EventStatus> states, List<Integer> categoriesId,
                                    LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query("""
         SELECT ev
         FROM Event AS ev
         WHERE (:categories IS NULL
                OR ev.category.id IN :categories)
           AND (:paid IS NULL
                OR ev.paid = :paid)
           AND ev.eventDate BETWEEN :rangeStart AND :rangeEnd
           AND (LOWER(ev.annotation) LIKE CONCAT('%', :text, '%')
                OR LOWER(ev.description) LIKE CONCAT('%', :text, '%'))
           AND (:onlyAvailable = FALSE
                OR (ev.participantLimit - ev.confirmedRequests) > 0)
           AND ev.state = 'PUBLISHED'
         """)
    List<Event> getAllEventsByUsers(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                    LocalDateTime rangeEnd, Boolean onlyAvailable, Pageable pageable);
}