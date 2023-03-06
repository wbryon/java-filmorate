package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmService {
    List<Film> getAllFilms();
    Film create(Film film);
    Film update(Film film);
    void addLike(Integer filmId, Integer userId);
    void deleteLike(Integer filmId, Integer userId);
    Collection<Film> findMostLikedFilms(Integer count);
    Film findFilmById(Integer filmId);
}
