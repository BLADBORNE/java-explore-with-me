package ru.practicum.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.user.model.User;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByIdIn(List<Integer> ids, Pageable pageable);

    List<User> findUserBy(Pageable pageable);

    Optional<User> findByEmailEquals(String email);
}