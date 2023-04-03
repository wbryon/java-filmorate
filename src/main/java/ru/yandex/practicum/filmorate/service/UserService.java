package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    public void create(User user) {
        userStorage.create(user);
    }

    public void update(User user) {
        userStorage.update(user);
    }

    public User findUserById(Integer userId) {
        return userStorage.findUserById(userId);
    }

    public void addFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        if (!user.getFriends().contains(friendId))
            throw new UserNotFoundException("Пользователя нет в списке друзей");
        user.getFriends().remove(user.getId());
        friend.getFriends().remove(friend.getId());
    }

    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        Set<Integer> friendIds = findUserById(id).getFriends();

        for(Integer friendId : friendIds) {
            User friend = findUserById(friendId);
            friends.add(friend);
        }
        return friends;
    }

    public List<User> findSharedFriends(int id, int otherId) {
        List<User> sharedFriends = new ArrayList<>();
        Set<Integer> userFriends = findUserById(id).getFriends();
        Set<Integer> otherFriends = findUserById(otherId).getFriends();
        Set<Integer> sharedIds = new HashSet<>(userFriends);
        sharedIds.retainAll(otherFriends);
        for(Integer userId : sharedIds)
            sharedFriends.add(findUserById(userId));
        return sharedFriends;
    }
}


