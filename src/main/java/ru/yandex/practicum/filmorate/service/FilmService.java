package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }

    public void addLike(Integer filmId, Integer userId) {
        if (userService.findUserById(userId) != null) {
            findFilmById(filmId).getLikes().add(userId);
        }
    }

    public void deleteLikeFromFilm(Integer filmId, Integer userId) {
        Film film = findFilmById(filmId);
        if (userService.findUserById(userId) != null) {
            film.getLikes().remove(userId);
        }
    }

    public List<Film> findMostLikedFilms(int count) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film findFilmById(int id) {
        Film film = filmStorage.findById(id);
        if (film == null)
            throw new FilmNotFoundException("Фильм не найден");
        return film;
    }
}
