package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * название не может быть пустым;
 * максимальная длина описания — 200 символов;
 * дата релиза — не раньше 28 декабря 1895 года;
 * продолжительность фильма должна быть положительной
 */
@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDateTime releaseDate;
    private Duration duration;
}
