package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void create(User user);
    void update(User user);
    User findUserById(Integer userId);
    void addFriend(Integer userId, Integer friendId);
    void removeFriend(Integer userId, Integer friendId);
    List<User> getFriends(int id);
    List<User> findSharedFriends(int id, int otherId);

}
