package ru.practicum.event.compilation.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
public class CompilationDto {
    private Integer id;
    private String title;
    private List<EventShortDto> events;
    private Boolean pinned;
}