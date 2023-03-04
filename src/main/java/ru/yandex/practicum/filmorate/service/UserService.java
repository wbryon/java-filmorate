package ru.yandex.practicum.filmorate.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * новые знания: @RequiredArgsConstructor(onConstructor = @__(@Autowired)) - аннотация заменяет конструктор класса
 */

@Slf4j
@Service
@Data
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.findById(id);
        User friend = userStorage.findById(friendId);
        if (user.getFriends().contains(friend))
            throw new UserAlreadyExistException("Этот пользователь уже был добавлен в друзья ранее");
        user.getFriends().add(friend);
        friend.getFriends().add(user);
    }
    public void removeFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        if (!user.getFriends().contains(friend))
            throw new UserNotFoundException("Пользователя нет в списке друзей");
        user.getFriends().remove(friendId);
        friend.getFriends().remove(userId);
    }

    public List<User> getFriends(int id) {
        return userStorage.findById(id).getFriends();
    }

    public List<User> findSharedFriends(int id, int otherId) {
        List<User> userFriends = userStorage.findById(id).getFriends();
        List<User> otherFriends = userStorage.findById(otherId).getFriends();
        return userFriends.stream().filter(user -> !otherFriends.contains(user))
                .collect(Collectors.toList());
    }
}


