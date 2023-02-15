package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static ru.yandex.practicum.filmorate.controller.FilmController.cinematographyBirthday;

@Data
public class Film {
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;
    private LocalDate releaseDate;
    @NonNull
    @Positive(message = "продолжительность фильма должна быть положительной")
    private Integer duration;

    public void setReleaseDate(LocalDate releaseDate) throws ValidationException {
        if (releaseDate.isBefore(cinematographyBirthday))
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года!");
        this.releaseDate = releaseDate;
    }

    public Film(int id, String name, String description, LocalDate releaseDate, @NonNull Integer duration)
            throws ValidationException {
        this.id = id;
        this.name = name;
        this.description = description;
        setReleaseDate(releaseDate);
        this.duration = duration;
    }
}
