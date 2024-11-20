package ru.practicum.event.compilation.mapper;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.event.compilation.dto.CompilationDto;
import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationMapper {
    private final EventMapper eventMapper;

    CompilationDto toDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();

        compilationDto.setId(compilation.getId());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setEvents(eventMapper.eventShortDtoList(compilation.getEvents()));
        compilationDto.setPinned(compilation.getPinned());

        return compilationDto;
    }

    Compilation fromDto(NewCompilationDto compilationDto, List<Event> events) {
        Compilation compilation = new Compilation();
        compilation.se

    }
}