package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    private String name;
    private String email;
    @NotBlank
    private String login;
//    @Past
    private LocalDate birthday;
}
