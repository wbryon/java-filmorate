package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * новые знания: @RequiredArgsConstructor(onConstructor = @__(@Autowired)) - аннотация заменяет конструктор класса
 */

@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int id, int friendId) {
        User user = userStorage.getAll().get(id);
        User friend = userStorage.getAll().get(friendId);
        if (!user.getFriendsIds().contains(friendId)) {
            user.getFriendsIds().add((long) friendId);
            user.getFriends().add(friend);
            userStorage.getAll().get(friendId).getFriendsIds().add((long) id);
            userStorage.getAll().get(friendId).getFriends().add(user);
        } else
            throw new  UserAlreadyExistException("Этот пользователь уже был добавлен в друзья ранее");
    }
    public void removeFriend(int userId, int friendId) {
        User user = userStorage.getAll().get(userId);
        User friend = userStorage.getAll().get(friendId);
        user.getFriends().remove(friendId);
        user.getFriendsIds().remove(friendId);
        friend.getFriends().remove(userId);
        friend.getFriendsIds().remove(userId);
    }

    public List<User> getFriends(int id) {
        return userStorage.getAll().get(id).getFriends();
    }

    public List<User> getSharedFriends(int id, int otherId) {
        List<User> userFriends = userStorage.getAll().get(id).getFriends();
        List<User> otherFriends = userStorage.getAll().get(otherId).getFriends();
        return userFriends.stream().filter(user -> !otherFriends.contains(user))
                .collect(Collectors.toList());
    }

    public UserStorage getUserStorage() {
        return userStorage;
    }
}


