package ru.yandex.practicum.filmorate.storage.db;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Component
public class GenreDbStorage implements GenreStorage {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    static final RowMapper<Genre> genreMapper = (rs, rowNum) ->
            new Genre(rs.getInt("genre_id"), rs.getString("genre_name"));

    public GenreDbStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        jdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        try {
            Genre genre = jdbcTemplate.queryForObject("SELECT * FROM GENRES WHERE genre_id=:id",
                    Map.of("id", id), genreMapper);
            return Optional.ofNullable(genre);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Genre> getAll() {
        return jdbcTemplate.query("SELECT * FROM GENRES", genreMapper);
    }
}
