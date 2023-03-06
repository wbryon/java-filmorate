package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InMemoryFilmService implements FilmService {
    private final FilmStorage filmStorage;
    private final InMemoryUserService userService;

    @Override
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @Override
    public Film create(Film film) {
        return filmStorage.create(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public void addLike(Integer filmId, Integer userId) {
        Film film = findFilmById(filmId);
        film.getLikes().add(userId);
    }

    @Override
    public void deleteLike(Integer filmId, Integer userId) {
        Film film = findFilmById(filmId);
        User user = userService.findUserById(userId);
        film.getLikes().remove(user.getId());
    }

    @Override
    public Collection<Film> findMostLikedFilms(Integer count) {
        List<Film> films = filmStorage.getAllFilms();
        return films.stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film findFilmById(Integer filmId) {
        return filmStorage.findFilmById(filmId);
    }
}
