package ru.practicum.event.compilation.service;

import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.model.Compilation;

public interface CompilationService {
    Compilation createNewCompilation(NewCompilationDto dto);

    void deleteCompilationById(int compilationId);

    Compilation updateCompilation(int compilationId);

    Compilation getAllCompilationsByPublicEndpoint(int compilationId);

    Compilation getAllCompilationByIdByPublicEndpoint(int compilationId);
}