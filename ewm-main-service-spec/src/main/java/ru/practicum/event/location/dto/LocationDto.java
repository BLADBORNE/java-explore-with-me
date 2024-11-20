package ru.practicum.event.location.dto;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class LocationDto {
    @NotNull(message = "Широта не должна быть пустой")
    private Float lat;
    @NotNull(message = "Долгота не должна быть пустой")
    private Float lon;
}