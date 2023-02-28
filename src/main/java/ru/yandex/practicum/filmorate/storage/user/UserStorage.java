package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Repository
public interface UserStorage {
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getAll();
    void validate(User user);
}
