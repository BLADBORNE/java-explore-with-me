package ru.practicum.event.compilation.mapper;

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

    public CompilationDto toDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();

        compilationDto.setId(compilation.getId());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setPinned(compilation.getPinned());

        if (compilation.getEvents() != null) {
            compilationDto.setEvents(eventMapper.eventShortDtoList(compilation.getEvents()));
        }

        return compilationDto;
    }

    public Compilation fromDto(NewCompilationDto compilationDto, List<Event> events) {
        Compilation compilation = new Compilation();

        compilation.setTitle(compilationDto.getTitle());

        if (compilationDto.getEvents() != null) {
            compilation.setEvents(events);
        }

        if (compilationDto.getPinned() != null && compilationDto.getPinned()) {
            compilation.setPinned(true);
        }

        return compilation;
    }

    public List<CompilationDto> toDtoList(List<Compilation> compilations) {
        return compilations.stream().map(this::toDto).toList();
    }
}