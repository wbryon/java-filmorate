package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Repository
public interface FilmStorage {
    void save(Film film);
    void update(Film film);
    List<Film> getAll();
    void validate(Film film);
}
