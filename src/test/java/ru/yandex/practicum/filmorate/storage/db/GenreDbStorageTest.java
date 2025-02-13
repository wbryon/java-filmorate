package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class GenreDbStorageTest {
    final GenreStorage genreStorage;

    @Test
    void shouldGetAll() {
        Collection<Genre> genres = genreStorage.getAll();
        assertEquals(6, genres.size());
    }

    @Test
    void shouldGetById() {
        Optional<Genre> genreOptional = genreStorage.getGenreById(1);
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", 1))
                .hasValueSatisfying(genre -> assertThat(genre).hasFieldOrPropertyWithValue("name", "Comedy"));
    }

    @Test
    void shouldNotGetWithWrongId() {
        Optional<Genre> genre = genreStorage.getGenreById(99);
        assertTrue(genre.isEmpty());
    }
}
