package ru.yandex.practicum.filmorate.exceptions;

public class FilmAlreadyLiked extends RuntimeException {
    public FilmAlreadyLiked(String message) {
        super(message);
    }
}
