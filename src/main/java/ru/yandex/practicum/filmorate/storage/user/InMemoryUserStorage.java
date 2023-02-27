package ru.yandex.practicum.filmorate.storage.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Data
public class InMemoryUserStorage implements UserStorage {
    private static final Map<Integer, User> users = new LinkedHashMap<>();
    private int userId = 0;
    @Override
    public void save(User user) {
        if (users.containsValue(user))
            throw new ValidationException("Такой пользователь уже существует");
        user.setId(++userId);
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь: {}", user.getName());
    }

    @Override
    public void update(User user) {
        if (!users.containsKey(user.getId()))
            throw new ValidationException("Пользователь с таким id не найден");
        users.put(user.getId(), user);
        log.info("Данные пользователя обновлены: {}", user.getName());
    }

    @Override
    public void delete(User user) {
        if (!users.containsKey(user.getId()))
            throw new ValidationException("Пользователь с таким id не найден");
        users.remove(user.getId());
        log.info("Пользователь удалён: {}", user.getName());
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void validate(User user) {
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        if (user.getLogin().contains(" "))
            throw new ValidationException("Логин пользователя не может содержать пробелы!");
    }
}
