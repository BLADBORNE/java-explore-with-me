package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import ru.practicum.event.location.dto.LocationDto;

import java.time.LocalDateTime;

@Getter
public abstract class AbstractUpdateEvent {
    @Size(min = 3, max = 120, message = "Заголовок должен быть не короче 3 и не более 120 символов")
    private String title;
    @Size(min = 20, max = 2000, message = "Короткое описание должно быть не короче 20 и не более 2000 символов")
    private String annotation;
    @Size(min = 20, max = 7000, message = "Описание должно быть не короче 20 и не более 7000 символов")
    private String description;
    private Integer category;
    private LocationDto location;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(message = "Дата не может быть в прошлом")
    private LocalDateTime eventDate;
    @PositiveOrZero(message = "Количество участниокв не может быть отрицательным")
    private Integer participantLimit;
    private Boolean requestModeration;
    private Boolean paid;
}