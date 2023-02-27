package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@Builder
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
    private Set<Long> friendsId;
    private List<User> friends;
}
