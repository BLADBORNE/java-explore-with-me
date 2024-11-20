package ru.practicum.category.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findCategoriesBy(Pageable pageable);

    Optional<Category> findByNameEquals(String name);
}