package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.ValidateService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    public static final LocalDate cinematographyBirthday = LocalDate.of(1895, 12, 28);
    private int filmId = 0;
    ValidateService validateService;

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        validateService.validateFilm(film);
        if (films.containsValue(film))
            throw new ValidationException("Такой фильм уже есть в коллекции!");
        film.setId(++filmId);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм: {}", film.getName());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws ValidationException {
        validateService.validateFilm(film);
        if (!films.containsKey(film.getId()))
            throw new ValidationException("Фильм с таким id не найден в коллекции!");
        films.put(film.getId(), film);
        log.info("Обновлены данные о фильме: {}", film.getName());
        return film;
    }
    @GetMapping
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }
}
