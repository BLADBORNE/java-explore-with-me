package ru.practicum.event.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.mapper.CompilationMapper;
import ru.practicum.event.compilation.model.Compilation;
import ru.practicum.event.compilation.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public Compilation createNewCompilation(NewCompilationDto dto) {
        dto.setEvents(dto.getEvents()
                .stream()
                .distinct()
                .toList());

        return compilationRepository.save(compilationMapper.fromDto(dto));
    }

    @Override
    public void deleteCompilationById(int compilationId) {

    }

    @Override
    public Compilation updateCompilation(int compilationId) {
        return null;
    }

    @Override
    public Compilation getAllCompilationsByPublicEndpoint(int compilationId) {
        return null;
    }

    @Override
    public Compilation getAllCompilationByIdByPublicEndpoint(int compilationId) {
        return null;
    }
}