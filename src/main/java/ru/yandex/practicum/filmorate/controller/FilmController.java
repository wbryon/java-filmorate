package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
        filmService.getFilmStorage().create(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        filmService.getFilmStorage().update(film);
        return film;
    }
    @GetMapping
    public List<Film> getFilms() {
        return filmService.getFilmStorage().getAllFilms();
    }

    @GetMapping(value = "/{id}")
    public Film findFilmById(@PathVariable("id") int filmId) {
        return filmService.getFilmStorage().findById(filmId);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable("id") int filmId, @PathVariable int userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLikeFromFilm(@PathVariable("id") int filmId, @PathVariable int userId) {
        filmService.deleteLikeFromFilm(filmId, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> findMostLikedFilms(@RequestParam(defaultValue = "10") Optional<Integer> count) {
            return filmService.findMostLikedFilms(count.orElse(10));
    }
}
