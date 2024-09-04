package ru.practicum.service;

import ru.practicum.model.Stats;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    Stats createNewStats(Stats stats);

    List<ViewStats> getViewStats(List<String> uri, LocalDateTime start, LocalDateTime end, boolean isUnique);
}