package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
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
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) throws ValidationException {
//        userService.validate(user);
//        userService.save(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws ValidationException {
//        userService.validate(user);
//        userService.update(user);
        return user;
    }

//    @GetMapping
//    public List<User> getUsers() {
//        return userService.getAll();
//    }
}
