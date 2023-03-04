package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Set;

@Repository
public interface FilmStorage {
    void create(Film film);
    void update(Film film);
    List<Film> getAllFilms();
    Film findById(int id);
    List<Integer> getFilmsIds();
    Set<Integer> getLikedUserIds();
    Set<Integer> addLike(Integer filmId, Integer userId);
    void removeLike(Integer filmId, Integer userId);
}
