package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;
import ru.practicum.user.service.validation.UserValidationService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserValidationService userValidationService;
    private final UserMapper userMapper;

    @Override
    public User createNewUser(NewUserDto newUserDto) {
        userValidationService.checkDuplicateEmails(newUserDto.getEmail());

        return userRepository.save(userMapper.toUser(newUserDto));
    }

    @Override
    public List<User> getUsers(List<Integer> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from / size, size);

        if (ids == null || ids.isEmpty()) {
            return userRepository.findUserBy(pageable);
        }

        return userRepository.findUserByIdIn(ids, pageable);
    }

    @Override
    public User getUserById(int id) {
        log.info("Получен запрос на отправку пользователя клиенту с id = {}", id);

        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String
                .format("Отсутствует клиент с id = %d", id)));
    }

    @Override
    public void deleteUserById(int id) {
        getUserById(id);

        userRepository.deleteById(id);
    }
}