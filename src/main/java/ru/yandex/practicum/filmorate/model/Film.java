package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
public class Film {
    private int id;
    @NotBlank(message = "У фильма отсутствует название!")
    private final String name;
    @Size(max = 200, message = "Длительность описания фильма не должна быть больше 200 символов!")
    private final String description;
    private LocalDate releaseDate;
    @NotNull
    @Positive(message = "Продолжительность фильма должна быть положительной!")
    private final int duration;
    private Mpa mpa;
    private Set<Integer> likes = new HashSet<>();
    private Set<Genre> genres = new TreeSet<>(Comparator.comparingInt(Genre::getId));

    public void setReleaseDate(LocalDate releaseDate) throws ValidationException {
        if (releaseDate.isBefore(LocalDate.of(1895, 12, 28)))
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года!");
        this.releaseDate = releaseDate;
    }
}
