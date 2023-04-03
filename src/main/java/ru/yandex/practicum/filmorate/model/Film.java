package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Film {
    private int id;
    @NotBlank(message = "У фильма отсутствует название!")
    private String name;
    @Size(max = 200, message = "Длительность описания фильма не должна быть больше 200 символов!")
    private String description;
    private LocalDate releaseDate;
    @NotNull
    @Positive(message = "Продолжительность фильма должна быть положительной!")
    private int duration;
    private Set<Integer> likes = new HashSet<>();
    private Mpa mpaRating;
    private List<Genre> genres;
}
