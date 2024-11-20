package ru.practicum.event.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.event.request.model.ParticipationRequestStatus;

import java.time.LocalDateTime;

@Setter
@Getter
public class ParticipationRequestDto {
    private Integer id;
    private Integer requester;
    private Integer event;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;
    private ParticipationRequestStatus status;
}