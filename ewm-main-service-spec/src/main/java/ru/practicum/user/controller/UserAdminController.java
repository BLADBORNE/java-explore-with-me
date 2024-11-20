package ru.practicum.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
@Slf4j
public class UserAdminController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/users")
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Valid NewUserDto newUserDto) {
        log.info("Получен запрос на создание пользователя");

        UserDto userDto = userMapper.toUserDto(userService.createNewUser(newUserDto));

        log.info("Успешно создан пользователь с id = {}", userDto.getId());

        return ResponseEntity.status(201).body(userDto);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "userId", required = false) Integer userId) {
        log.info("Получен запрос на удаление пользователя по id = {}", userId);

        userService.deleteUserById(userId);

        log.info("Успешно уделен пользователь с id = {}", userId);

        return ResponseEntity.status(204).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(name = "ids", required = false) List<Integer> ids,
            @RequestParam(name = "from", defaultValue = "0") Integer from,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        log.info("Получен запрос на отправку пользователей с параметрами: ids: {}, from: {}, size: {}", ids == null ?
                null : ids.toString(), from, size);

        List<UserDto> userDto = userMapper.toUserDto(userService.getUsers(ids,from,size));

        log.info("Успешно отправлены пользователи по параметрам");

        return ResponseEntity.ok().body(userDto);
    }
}