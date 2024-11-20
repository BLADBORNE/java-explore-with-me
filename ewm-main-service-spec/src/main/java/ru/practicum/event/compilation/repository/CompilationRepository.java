package ru.practicum.event.compilation.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.event.compilation.model.Compilation;

import java.util.List;

public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    @Query("""
            SELECT cp
            FROM Compilation AS cp
            WHERE (:pinned IS NULL
                   OR cp.pinned = :pinned)
            """)
    List<Compilation> getAllCompilationsByStates(Boolean pinned, Pageable pageable);
}