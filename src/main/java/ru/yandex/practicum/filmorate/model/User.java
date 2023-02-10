package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * электронная почта не может быть пустой и должна содержать символ @;
 * логин не может быть пустым и содержать пробелы;
 * имя для отображения может быть пустым — в таком случае будет использован логин;
 * дата рождения не может быть в будущем
 */
@Data
public class User {
    private int id;
    private String name;
    private String email;
    private String login;
    private LocalDateTime birthday;
}
