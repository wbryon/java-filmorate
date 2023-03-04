package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyLiked;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Comparator;
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

    public void addLikeToFilm(int filmId, int userId) {
        Film film = filmStorage.findById(filmId);
        if (!film.getLikedUserIds().contains(userId))
            throw  new FilmAlreadyLiked("Этот пользователь уже ставил лайк фильму");
            film.getLikedUserIds().add(userId);
            film.setLikes(film.getLikedUserIds());

    }

    public void deleteLikeFromFilm(int filmId, int userId) {
        if (userId <= 0)
            throw new UserNotFoundException("id пользователя должен быть больше нуля");
        filmStorage.findById(filmId).getLikedUserIds().remove(userId);
    }

    public List<Film> findMostLikedFilms(Integer count) {
        if (count <= 0) {
            throw new ValidationException("Количество фильмов должно быть больше 0");
        }
//        if (filmStorage.getAllFilms().size() <= count) {
//            return filmStorage.getAllFilms().stream().sorted(Comparator.comparingInt(film -> - film.getLikes().size()))
//                    .collect(Collectors.toList());
//        }
        return filmStorage.getAllFilms().stream().sorted(Comparator.comparingInt(film -> - film.getLikes().size()))
                .limit(count).collect(Collectors.toList());
    }
}
