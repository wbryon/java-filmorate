package ru.yandex.practicum.filmorate.storage.db;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class MpaDbStorageTest {

    final MpaStorage mpaStorage;

    @Test
    void shouldGetAll() {
        Collection<Mpa> mpa = mpaStorage.getAll();
        assertEquals(5, mpa.size());
    }

    @Test
    void shouldGetById() {
        Optional<Mpa> genreOptional = mpaStorage.getMpaById(4);
        assertThat(genreOptional)
                .isPresent()
                .hasValueSatisfying(mpa -> assertThat(mpa).hasFieldOrPropertyWithValue("id", 4))
                .hasValueSatisfying(mpa -> assertThat(mpa).hasFieldOrPropertyWithValue("name", "R"));
    }

    @Test
    void shouldNotGetWithWrongId() {
        Optional<Mpa> mpa = mpaStorage.getMpaById(6);
        assertTrue(mpa.isEmpty());
    }
}
