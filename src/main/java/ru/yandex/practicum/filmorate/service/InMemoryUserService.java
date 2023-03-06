package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InMemoryUserService implements UserService {
    private final UserStorage userStorage;

    @Override
    public List<User> getAllUsers() {
        return userStorage.getAll();
    }

    @Override
    public void create(User user) {
        userStorage.create(user);
    }

    @Override
    public void update(User user) {
        userStorage.update(user);
    }

    @Override
    public User findUserById(Integer userId) {
        return userStorage.findUserById(userId);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        if (!user.getFriends().contains(friendId))
            throw new UserNotFoundException("Пользователя нет в списке друзей");
        user.getFriends().remove(user.getId());
        friend.getFriends().remove(friend.getId());
    }

    @Override
    public List<User> getFriends(int id) {
        List<User> friends = new ArrayList<>();
        Set<Integer> friendIds = findUserById(id).getFriends();

        for(Integer friendId : friendIds) {
            User friend = findUserById(friendId);
            friends.add(friend);
        }
        return friends;
    }

    @Override
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


