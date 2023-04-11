package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    List<Film> getAllFilms();

    Collection<Film> findMostLikedFilms(int count);

    Film findFilmById(int id);
}
