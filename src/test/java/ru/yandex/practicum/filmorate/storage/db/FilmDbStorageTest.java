package ru.yandex.practicum.filmorate.storage.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureTestDatabase
class FilmDbStorageTest {

    final FilmStorage filmStorage;

    public FilmDbStorageTest(@Qualifier("FilmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @Test
    void shouldCreate() {
        Film film1 = new Film("John Wick", "John Wick is a 2014 American neo-noir action thriller film" +
                "directed by Chad Stahelski in his feature directorial debut and written by Derek Kolstad",101);
        film1.setReleaseDate(LocalDate.of(2014, 10, 24));
        film1.setMpa(new Mpa(1, null));
        Film filmWithId = filmStorage.create(film1);
        assertEquals(1, filmWithId.getId());
        assertEquals("G", filmWithId.getMpa().getName());

        Film film2 = new Film("The Matrix", "The Matrix is a 1999 science fiction action film written" +
                " and directed by the Wachowskis.", 136);
        film2.setReleaseDate(LocalDate.of(1999, 3, 31));
        film2.setMpa(new Mpa(2, null));
        Film filmWithId2 = filmStorage.create(film2);
        assertEquals(2, filmWithId2.getId());
        assertEquals("PG", filmWithId2.getMpa().getName());
    }

    @Test
    void shouldUpdate() {
        Film film = new Film("Updated movie", "Updated description", 100);
        film.setReleaseDate(LocalDate.of(2020, 2, 2));
        film.setMpa(new Mpa(1, null));
        film.setId(1);
        Film filmFromDb = filmStorage.update(film);
        assertEquals(1, filmFromDb.getId());
        assertEquals(film.getName(), filmFromDb.getName());
    }

    @Test
    void shouldGetAllFilms() {
        Collection<Film> films = filmStorage.getAllFilms();
        assertEquals(2, films.size());
    }

    @Test
    void shouldFindFilmById() {
        assertEquals("The Matrix", filmStorage.findFilmById(2).getName());
    }

    @Test
    void shouldNotFindByWithWrongId() {
        assertNull(filmStorage.findFilmById(-1));
    }
}