package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("SELECT new ru.practicum.model.ViewStats(st.app, st.uri, COUNT(st.ip)) " +
            "FROM EndpointHit AS st " +
            "WHERE st.uri IN :uri " +
            "  AND st.timestamp BETWEEN :start AND :end " +
            "GROUP BY st.app, st.uri " +
            "ORDER BY COUNT(st.ip) DESC")

    List<ViewStats> getViewStats(List<String> uri, LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.model.ViewStats(st.app, st.uri, COUNT(DISTINCT st.ip))" +
            "FROM EndpointHit AS st " +
            "WHERE st.uri IN :uri" +
            "  AND st.timestamp BETWEEN :start AND :end " +
            "GROUP BY st.app, st.uri " +
            "ORDER BY COUNT(DISTINCT st.ip) DESC")
    List<ViewStats> getUniqueViewStats(List<String> uri, LocalDateTime start, LocalDateTime end);


    @Query("SELECT new ru.practicum.model.ViewStats(st.app, st.uri, COUNT(st.ip)) " +
            "FROM EndpointHit AS st " +
            "WHERE st.timestamp BETWEEN :start AND :end " +
            "GROUP BY st.app, st.uri " +
            "ORDER BY COUNT(st.ip) DESC")

    List<ViewStats> getAllViewStats(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.model.ViewStats(st.app, st.uri, COUNT(DISTINCT st.ip))" +
            "FROM EndpointHit AS st " +
            "WHERE st.timestamp BETWEEN :start AND :end " +
            "GROUP BY st.app, st.uri " +
            "ORDER BY COUNT(DISTINCT st.ip) DESC")
    List<ViewStats> getAllUniqueViewStats(LocalDateTime start, LocalDateTime end);
}