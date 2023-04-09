package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new LinkedHashMap<>();
    private static final LocalDate cinematographyBirthday = LocalDate.of(1895, 12, 28);
    private Integer filmId = 0;

    @Override
    public Film create(Film film) {
        if (films.containsValue(film))
            throw new ValidationException("Такой фильм уже есть в коллекции!");
        validate(film);
        film.setId(++filmId);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм: {}", film.getName());
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId()))
            throw new FilmNotFoundException("Фильм с таким id не найден в коллекции!");
        validate(film);
        films.put(film.getId(), film);
        log.info("Обновлены данные о фильме: {}", film.getName());
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film findFilmById(int id) {
        if (!films.containsKey(id))
            throw new FilmNotFoundException("Фильм с таким id не найден в коллекции!");
        return films.get(id);
    }

    @Override
    public Collection<Film> findMostLikedFilms(int count) {
        return getAllFilms().stream()
                .sorted((film1, film2) -> film2.getLikes().size() - film1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(cinematographyBirthday))
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года!");
    }
}
