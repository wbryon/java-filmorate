package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import static ru.yandex.practicum.filmorate.controller.FilmController.cinematographyBirthday;

@Component
public class ValidateService {

    public static void validateFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(cinematographyBirthday))
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года!");
    }

    public static void validateUser(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        if (user.getLogin().contains(" "))
            throw new ValidationException("Логин пользователя не может содержать пробелы!");
    }
}