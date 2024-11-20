package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import ru.practicum.event.location.dto.LocationDto;

import java.time.LocalDateTime;

@Getter
public class NewEventDto {
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 3, max = 120, message = "Заголовок должен быть не короче 3 и не более 120 символов")
    private String title;
    @NotBlank(message = "Краткое описание не может быть пустым")
    @Size(min = 20, max = 2000, message = "Короткое описание должно быть не короче 20 и не более 2000 символов")
    private String annotation;
    @NotBlank(message = "Описание не может быть пустым")
    @Size(min = 20, max = 7000, message = "Описание должно быть не короче 20 и не более 7000 символов")
    private String description;
    @NotNull(message = "Событию обязательно должна соответствовать категория")
    private Integer category;
    @NotNull(message = "У события обязательна должна быть указана локация")
    private LocationDto location;
    @NotNull(message = "Дата события не может быть пуста")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent
    private LocalDateTime eventDate;
    @PositiveOrZero(message = "Количество участниокв не может быть отрицательным")
    private Integer participantLimit;
    private Boolean requestModeration;
    private Boolean paid;
}