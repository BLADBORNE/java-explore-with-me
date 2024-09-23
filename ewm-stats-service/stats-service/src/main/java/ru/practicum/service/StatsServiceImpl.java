package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;
import ru.practicum.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public EndpointHit createNewStats(EndpointHit hit) {
        log.info("Получен запрос на создание нового события");

        return statsRepository.save(hit);
    }

    @Override
    public List<ViewStats> getViewStats(List<String> uri, LocalDateTime start, LocalDateTime end, boolean isUnique) {
        if (uri != null && !uri.isEmpty()) {
            return isUnique
                    ? statsRepository.getUniqueViewStats(uri, start, end)
                    : statsRepository.getViewStats(uri, start, end);
        } else {
            return isUnique
                    ? statsRepository.getAllViewStats(start, end)
                    : statsRepository.getAllUniqueViewStats(start, end);
        }
    }
}