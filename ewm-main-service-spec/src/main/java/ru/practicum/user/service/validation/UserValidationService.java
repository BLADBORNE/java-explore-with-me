package ru.practicum.user.service.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.user.exception.AlreadyExistException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserValidationService {
    private final UserRepository userRepository;

    public void checkDuplicateEmails(String email) {
        Optional<User> user = userRepository.findByEmailEquals(email);

        if (user.isPresent()) {
            log.warn("Попытка создать пользователя с занятым email");

            throw new AlreadyExistException(String.format("Пользователь с данным email %s уже есть", email));
        }
    }
}