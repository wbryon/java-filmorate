package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;


@Slf4j
@Service
public class UserService {
    private final UserStorage userStorage;

    public UserService(@Qualifier("UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User findUserById(Integer userId) {
        User user = userStorage.findUserById(userId);
        if (user == null)
            throw new UserNotFoundException("Пользователь с id = " + userId + " не найден");
        return user;
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        user.getFriends().put(friend.getId(), "Запрос принят");
        userStorage.update(user);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        if (!user.getFriends().containsKey(friendId))
            throw new UserNotFoundException("Пользователя нет в списке друзей");
        user.getFriends().remove(friendId);
        userStorage.update(user);
    }

    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        Map<Integer, String> friendIds = findUserById(id).getFriends();

        for(Integer friendId : friendIds.keySet()) {
            User friend = findUserById(friendId);
            friends.add(friend);
        }
        return friends;
    }

    public List<User> findSharedFriends(int id, int otherId) {
        List<User> sharedFriends = new ArrayList<>();
        Map<Integer, String> userFriends = findUserById(id).getFriends();
        Map<Integer, String> otherFriends = findUserById(otherId).getFriends();
        Set<Integer> sharedIds = new HashSet<>(userFriends.keySet());
        sharedIds.retainAll(otherFriends.keySet());
        for(Integer userId : sharedIds)
            sharedFriends.add(findUserById(userId));
        return sharedFriends;
    }
}
