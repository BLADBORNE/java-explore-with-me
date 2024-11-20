package ru.practicum.event.compilation.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.event.compilation.dto.CompilationDto;
import ru.practicum.event.compilation.mapper.CompilationMapper;
import ru.practicum.event.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping("/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationUserController {
    private final CompilationService compilationService;
    private final CompilationMapper compilationMapper;

    @GetMapping
    public ResponseEntity<List<CompilationDto>> getAllCompilationByIdByPublicEndpoint(
            @RequestParam(value = "pinned", required = false) Boolean pinned,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на получение подборки с параметрами: pinned {} from {} size {}", pinned, from, size);

        List<CompilationDto> dtoList = compilationMapper.toDtoList(compilationService
                .getAllCompilationsByPublicEndpoint(pinned, from, size));

        log.info("Успешно отрпавлены подборки");

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{compId}")
    public ResponseEntity<CompilationDto> getAllCompilationsByPublicEndpoint(
            @PathVariable(value = "compId") Integer compId
    ) {
        log.info("Получен запрос на получение подборки с id = {}", compId);

        CompilationDto dto = compilationMapper.toDto(compilationService.getAllCompilationByIdByPublicEndpoint(compId));

        log.info("Успешно отрпавлена подборка с id = {}", compId);

        return ResponseEntity.ok(dto);
    }
}