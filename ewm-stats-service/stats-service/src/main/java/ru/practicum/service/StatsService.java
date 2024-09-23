package ru.practicum.service;

import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHit createNewStats(EndpointHit hit);

    List<ViewStats> getViewStats(List<String> uri, LocalDateTime start, LocalDateTime end, boolean isUnique);
}