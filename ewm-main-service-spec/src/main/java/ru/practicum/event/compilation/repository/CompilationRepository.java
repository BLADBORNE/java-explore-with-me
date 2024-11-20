package ru.practicum.event.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
}