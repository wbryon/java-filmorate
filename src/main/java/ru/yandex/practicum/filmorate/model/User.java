package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class User {
    private int id;
    private String name;
    @NotBlank
    @Email(message = "Невалидная электронная почта - отсутствует символ '@'")
    private String email;
    @NotBlank
    private String login;
    private LocalDate birthday;
}
