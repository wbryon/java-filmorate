package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.InMemoryFilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/films")
public class FilmController {
    final InMemoryFilmService filmService;

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        filmService.create(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        filmService.update(film);
        return film;
    }
    @GetMapping
    public List<Film> getFilms() {
        return filmService.getAllFilms();
    }

    @GetMapping(value = "/{id}")
    public Film findFilmById(@PathVariable("id") Integer filmId) {
        return filmService.findFilmById(filmId);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer filmId, @PathVariable Integer userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer filmId, @PathVariable Integer userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping(value = "/popular")
    public Collection<Film> findMostLikedFilms(@RequestParam(defaultValue = "10") Optional<Integer> count) {
            return filmService.findMostLikedFilms(count.orElse(10));
    }
}
