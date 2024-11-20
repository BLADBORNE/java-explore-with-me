package ru.practicum.event.compilation.service;

import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.event.compilation.model.Compilation;

import java.util.List;

public interface CompilationService {
    Compilation createNewCompilation(NewCompilationDto dto);

    void deleteCompilationById(int compilationId);

    Compilation updateCompilation(int compilationId, UpdateCompilationRequestDto updatedDto);

    List<Compilation> getAllCompilationsByPublicEndpoint(Boolean pinned, int from, int size);

    Compilation getAllCompilationByIdByPublicEndpoint(int compilationId);
}