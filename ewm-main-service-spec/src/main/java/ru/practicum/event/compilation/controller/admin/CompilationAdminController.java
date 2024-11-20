package ru.practicum.event.compilation.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.event.compilation.dto.CompilationDto;
import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.mapper.CompilationMapper;
import ru.practicum.event.compilation.service.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @PostMapping
    public ResponseEntity<CompilationDto> createCompilation(@RequestBody @Valid NewCompilationDto dto) {
        log.info("Получен запрос на создание подборки для событий: {}", dto.getEvents() != null ? dto.getEvents()
                .toString() : null);

        CompilationDto compilationDto = compilationMapper.toDto(compilationService.createNewCompilation(dto));

        log.info("Успешно создана подборка с id = {}", compilationDto.getId());

        return ResponseEntity.ok(compilationDto);
    }
}