package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyLiked;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Map<Integer, Film> films = new LinkedHashMap<>();
    private static final Set<Integer> likedUserIds = new HashSet<>();
    public static final LocalDate cinematographyBirthday = LocalDate.of(1895, 12, 28);
    private int filmId = 0;

    public void create(Film film) {
        validate(film);
        if (films.containsValue(film))
            throw new ValidationException("Такой фильм уже есть в коллекции!");
        film.setId(++filmId);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм: {}", film.getName());
    }

    public void update(Film film) {
        validate(film);
        if (!films.containsKey(film.getId()))
            throw new FilmNotFoundException("Фильм с таким id не найден в коллекции!");
        films.put(film.getId(), film);
        log.info("Обновлены данные о фильме: {}", film.getName());
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findById(int id) {
        if (!films.containsKey(id))
            throw new FilmNotFoundException("Фильм с таким id не найден в коллекции!");
        return films.get(id);
    }

    @Override
    public List<Integer> getFilmsIds() {
        return new ArrayList<>(films.keySet());
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(cinematographyBirthday))
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года!");
    }

    public Set<Integer> getLikedUserIds() {
        return likedUserIds;
    }

    @Override
    public Set<Integer> addLike(Integer filmId, Integer userId) {
        if (!films.containsKey(filmId))
            throw new FilmNotFoundException("Фильм не найден в коллекции!");
        Set<Integer> likes = films.get(filmId).getLikes();
        if (likes.contains(userId))
            throw new FilmAlreadyLiked("Пользователь уже поставил лайк фильму");
        likes.add(userId);
        return likes;
    }

    @Override
    public void removeLike(Integer filmId, Integer userId) {

    }
}
