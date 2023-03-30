package ru.yandex.practicum.filmorate.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbUserService implements UserService {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public User findUserById(Integer userId) {
        return null;
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {

    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {

    }

    @Override
    public List<User> getFriends(int id) {
        return null;
    }

    @Override
    public List<User> findSharedFriends(int id, int otherId) {
        return null;
    }
}
