package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static final Map<Integer, Film> films = new LinkedHashMap<>();
    public static final LocalDate cinematographyBirthday = LocalDate.of(1895, 12, 28);
    private int filmId = 0;

    public void save(Film film) {
        if (films.containsValue(film))
            throw new ValidationException("Такой фильм уже есть в коллекции!");
        film.setId(++filmId);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм: {}", film.getName());
    }

    public void update(Film film) {
        if (!films.containsKey(film.getId()))
            throw new ValidationException("Фильм с таким id не найден в коллекции!");
        films.put(film.getId(), film);
        log.info("Обновлены данные о фильме: {}", film.getName());
    }

    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    public void validate(Film film) {
        if (film.getReleaseDate().isBefore(cinematographyBirthday))
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года!");
    }
}
