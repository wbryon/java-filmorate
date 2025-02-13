package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public FilmService(@Qualifier("FilmDbStorage") FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public void addLike(Integer filmId, Integer userId) {
        Film film = findFilmById(filmId);
        User user = userService.findUserById(userId);
        film.getLikes().add(user.getId());
        filmStorage.update(film);
    }

    public void deleteLike(Integer filmId, Integer userId) {
        Film film = findFilmById(filmId);
        User user = userService.findUserById(userId);
        film.getLikes().remove(user.getId());
        filmStorage.update(film);
    }

    public Collection<Film> findMostLikedFilms(int count) {
        return filmStorage.findMostLikedFilms(count);
    }

    public Film findFilmById(Integer filmId) {
        Film film = filmStorage.findFilmById(filmId);
        return Optional.ofNullable(filmStorage.findFilmById(filmId))
                .orElseThrow(() -> new FilmNotFoundException("Film not found!"));
    }
}
