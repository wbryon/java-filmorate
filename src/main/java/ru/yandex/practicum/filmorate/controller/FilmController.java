package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "/films")
public class FilmController {
    final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        filmService.validate(film);
        filmService.save(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws ValidationException {
        filmService.validate(film);
        filmService.update(film);
        return film;
    }
    @GetMapping
    public List<Film> getFilms() {
        return filmService.getAll();
    }
}
