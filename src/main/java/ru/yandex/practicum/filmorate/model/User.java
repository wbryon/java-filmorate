package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
public class User {
    private Integer id;
    private String name;
    @NotBlank
    @Email(message = "Невалидная электронная почта - отсутствует символ '@'")
    private String email;
    @NotBlank(message = "Логин пользователя не может быть пустым!")
    private String login;
    @Past(message = "Дата рождения не может быть в будущем!")
    private LocalDate birthday;
    private Map<Integer, String> friends = new HashMap<>();

    public void setName(String name) {
        if (name == null || name.isBlank())
            this.name = login;
        else
            this.name = name;
    }
}
