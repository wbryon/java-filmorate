package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@Repository
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer userId = 0;

    @Override
    public User create(User user) {
        if (users.containsValue(user))
            throw new ValidationException("Такой пользователь уже существует");
        validate(user);
        user.setId(++userId);
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь: {}", user.getName());
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId()))
            throw new UserNotFoundException("Пользователь с таким id не найден");
        validate(user);
        users.put(user.getId(), user);
        log.info("Данные пользователя обновлены: {}", user.getName());
        return user;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(int id) {
        if (users.get(id) == null)
            throw new UserNotFoundException("Пользователь не найден");
        return users.get(id);
    }

    private void validate(User user) {
        if (user.getLogin().contains(" "))
            throw new ValidationException("Логин пользователя не может содержать пробелы!");
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
