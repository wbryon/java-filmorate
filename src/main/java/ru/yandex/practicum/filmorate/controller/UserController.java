package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ValidateService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 0;
    ValidateService validateService;

    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {
        validateService.validateUser(user);
        if (users.containsValue(user))
            throw new ValidationException("Такой пользователь уже существует");
        user.setId(++userId);
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь: {}", user.getName());
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws ValidationException {
        validateService.validateUser(user);
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
