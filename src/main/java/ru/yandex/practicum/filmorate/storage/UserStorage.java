package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    void create(User user);
    void update(User user);
    List<User> getAll();
    User findUserById(Integer id);
}
