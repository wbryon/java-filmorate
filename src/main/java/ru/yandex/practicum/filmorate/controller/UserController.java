package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private static int userId = 0;

    @PostMapping
    public User createUser(@RequestBody @Valid User user) throws ValidationException {
        Validator.validateUser(user);
        user.setId(++userId);
        if (users.containsValue(user))
            throw new ValidationException("Такой пользователь уже существует");
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь: {}", user.getName());
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) throws ValidationException {
        Validator.validateUser(user);
        if (!users.containsKey(user.getId()))
            throw new ValidationException("Пользователь с таким id не найден");
        users.put(user.getId(), user);
        log.info("Данные пользователя обновлены: {}", user.getName());
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
