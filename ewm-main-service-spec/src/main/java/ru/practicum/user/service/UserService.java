package ru.practicum.user.service;

import ru.practicum.user.dto.NewUserDto;
import ru.practicum.user.model.User;

import java.util.List;

public interface UserService {
    User createNewUser(NewUserDto user);

    User getUserById(int id);

    List<User> getUsers(List<Integer> ids, int from, int size);

    void deleteUserById(int id);
}