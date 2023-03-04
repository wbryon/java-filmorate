package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {
        userService.getUserStorage().create(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws ValidationException {
        userService.getUserStorage().update(user);
        return user;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUserStorage().getAll();
    }

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable("id") Integer userId) {
        return userService.getUserStorage().findById(userId);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public void addFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        userService.removeFriend(id, friendId);
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> getUserFriends(@PathVariable int id) {
        return userService.getFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> getSharedFriends(@PathVariable int id, @PathVariable int otherId) {
        return userService.findSharedFriends(id, otherId);
    }
}
