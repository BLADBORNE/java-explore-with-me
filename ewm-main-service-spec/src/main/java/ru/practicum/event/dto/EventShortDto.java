package ru.practicum.event.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventShortDto {
    private Integer id;
    private String title;
    private String annotation;
    private CategoryDto category;
    private UserShortDto initiator;
    private LocalDateTime eventDate;
    private Integer confirmedRequests;
    private Integer views;
    private Boolean paid;
}