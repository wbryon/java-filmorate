package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Slf4j
@Service
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(User user) {
        userStorage.getAll();
    }
    public void removeFriend(User user) {}

    public List<User> getFriends(User user) {
        return user.getFriends();
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}
