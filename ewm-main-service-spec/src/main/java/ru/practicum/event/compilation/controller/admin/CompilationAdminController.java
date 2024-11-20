package ru.practicum.event.compilation.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import ru.practicum.event.compilation.dto.CompilationDto;
import ru.practicum.event.compilation.dto.NewCompilationDto;
import ru.practicum.event.compilation.dto.UpdateCompilationRequestDto;
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

        return ResponseEntity.status(201).body(compilationDto);
    }

    @PatchMapping("/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(
            @PathVariable(value = "compId") Integer compId,
            @RequestBody @Valid UpdateCompilationRequestDto dto) {
        log.info("Получен запрос на обновление подборки для событий: {}", dto.getEvents() != null ? dto.getEvents()
                .toString() : null);

        CompilationDto compilationDto = compilationMapper.toDto(compilationService.updateCompilation(compId, dto));

        log.info("Успешно обновлена подборка с id = {}", compilationDto.getId());

        return ResponseEntity.ok(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Void> deleteCompilationById(
            @PathVariable(value = "compId") Integer compId
    ) {
        log.info("Получен запрос на удаление подборки с id = {}", compId);

        compilationService.deleteCompilationById(compId);

        log.info("Успешно удалена подборка с id = {}", compId);

        return ResponseEntity.status(204).build();
    }
}