package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        filmService.getFilmStorage().validate(film);
        filmService.getFilmStorage().save(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws ValidationException {
        filmService.getFilmStorage().validate(film);
        filmService.getFilmStorage().update(film);
        return film;
    }
    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilmStorage().getAll();
    }


}
