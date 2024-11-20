package ru.practicum.event.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.event.compilation.mapper.CompilationMapper;
import ru.practicum.event.compilation.model.Compilation;
import ru.practicum.event.compilation.repository.CompilationRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.service.EventService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final EventService eventService;

    @Transactional
    @Override
    public Compilation createNewCompilation(NewCompilationDto dto) {
        List<Event> events = eventService.getEventsByIdIn(getUniqueEvents(dto.getEvents()));

        return compilationRepository.save(compilationMapper.fromDto(dto, events));
    }

    @Override
    public void deleteCompilationById(int compilationId) {
        getAllCompilationByIdByPublicEndpoint(compilationId);

        compilationRepository.deleteById(compilationId);
    }

    @Override
    public Compilation updateCompilation(int compilationId, UpdateCompilationRequestDto dto) {
        Compilation compilation = getAllCompilationByIdByPublicEndpoint(compilationId);

        List<Event> events = eventService.getEventsByIdIn(getUniqueEvents(dto.getEvents()));

        updateCompilationsFields(compilation, dto, events);

        return compilationRepository.save(compilation);
    }

    @Override
    public List<Compilation> getAllCompilationsByPublicEndpoint(Boolean pinned, int from, int size) {

        return compilationRepository.getAllCompilationsByStates(pinned, PageRequest.of(from / size, size));
    }

    @Override
    public Compilation getAllCompilationByIdByPublicEndpoint(int compilationId) {
        return compilationRepository.findById(compilationId).orElseThrow(() -> new NoSuchElementException(String
                .format("Подборка с id = %d не найдена", compilationId)));
    }

    private List<Integer> getUniqueEvents(List<Integer> eventsId) {
        if (eventsId != null) {
            return eventsId
                    .stream()
                    .distinct()
                    .toList();
        }

        return new ArrayList<>();
    }

    private void updateCompilationsFields(Compilation compilation, UpdateCompilationRequestDto updatedDto,
                                          List<Event> events) {
        if (updatedDto.getTitle() != null) {
            compilation.setTitle(updatedDto.getTitle());
        }

        if (updatedDto.getEvents() != null) {
            compilation.setEvents(events);
        }

        if (updatedDto.getPinned() != null && updatedDto.getPinned()) {
            compilation.setPinned(true);
        }
    }
}