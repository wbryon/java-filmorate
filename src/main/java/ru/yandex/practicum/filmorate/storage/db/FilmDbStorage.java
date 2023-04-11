package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

import static ru.yandex.practicum.filmorate.storage.db.GenreDbStorage.genreMapper;
import static ru.yandex.practicum.filmorate.storage.db.MpaDbStorage.mpaMapper;

@Slf4j
@Component("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String FILMS = "SELECT f.name f_name, description, " +
            "release_date, duration, rate, f.film_id, m.mpa_id, m.category, g.genre_id, g.genre_name, l.user_id " +
            "FROM FILMS f LEFT JOIN MPA m ON f.mpa_id=m.mpa_id " +
            "LEFT JOIN FILM_GENRE fg ON f.film_id=fg.film_id " +
            "LEFT JOIN GENRES g ON fg.genre_id=g.genre_id " +
            "LEFT JOIN LIKES l ON f.film_id=l.film_id";
    public FilmDbStorage(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", film.getName())
                .addValue("description", film.getDescription())
                .addValue("release_date", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("rate", film.getLikes().size())
                .addValue("mpa", film.getMpa().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("INSERT INTO FILMS (name, description, release_date, duration, mpa_id, rate) " +
                "VALUES (:name, :description, :release_date, :duration, :mpa, :rate)", sqlParameterSource, keyHolder);
        int filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        likesUpdater(film);
        Set<Genre> genres = film.getGenres();
        genreUpdater(filmId, genres);
        return findFilmById(filmId);
    }

    @Override
    public Film update(Film film) {
        int filmId = film.getId();
        try {
            jdbcTemplate.queryForObject("SELECT film_id FROM FILMS WHERE film_id=:id",
                    Map.of("id", filmId), Integer.class);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserNotFoundException("Неверный id");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", filmId)
                .addValue("description", film.getDescription())
                .addValue("name", film.getName())
                .addValue("release_date", film.getReleaseDate())
                .addValue("duration", film.getDuration())
                .addValue("rate", film.getLikes().size())
                .addValue("mpa", film.getMpa().getId());
        jdbcTemplate.update("UPDATE FILMS SET film_id=:id, name=:name, description=:description," +
                "release_date=:release_date, duration=:duration, mpa_id=:mpa, rate=:rate " +
                "WHERE film_id=:id", sqlParameterSource);
        jdbcTemplate.update("DELETE FROM LIKES WHERE film_id=:id", Map.of("id", filmId));
        likesUpdater(film);
        jdbcTemplate.update("DELETE FROM FILM_GENRE WHERE film_id = :id", Map.of("id", filmId));
        Set<Genre> genres = film.getGenres();
        genreUpdater(filmId, genres);
        return findFilmById(film.getId());
    }

    @Override
    public List<Film> getAllFilms() {
        return jdbcTemplate.query(FILMS, filmDataExtractor);
    }

    @Override
    public Film findFilmById(int id) {
        String sql = FILMS + " WHERE f.film_id=:id";
        List<Film> film = jdbcTemplate.query(sql, Map.of("id", id), filmDataExtractor);
        if (film.isEmpty())
            return null;
        return film.get(0);
    }

    @Override
    public Collection<Film> findMostLikedFilms(int count) {
        return jdbcTemplate.query(FILMS + " WHERE f.film_id IN (SELECT film_id FROM FILMS ORDER BY rate " +
                "DESC LIMIT :size)", Map.of("size", count), filmDataExtractor);
    }

    private final ResultSetExtractor<List<Film>> filmDataExtractor = rs -> {
        Map<Integer, Film> filmMap = new LinkedHashMap<>();
        Film film;
        while (rs.next()) {
            Integer filmId = rs.getInt("film_id");
            film = filmMap.get(filmId);
            if (film == null) {
                String name = rs.getString("f_name");
                String description = rs.getString("description");
                Integer duration = rs.getInt("duration");
                film = new Film(name, description, duration);
                film.setReleaseDate(rs.getDate("release_date").toLocalDate());
                film.setId(rs.getInt("film_id"));
                film.setMpa(mpaMapper.mapRow(rs, 0));
                filmMap.put(filmId, film);
            }
            Set<Genre> genres = film.getGenres();
            if (rs.getInt("genre_id") != 0)
                genres.add(genreMapper.mapRow(rs, 0));
            Set<Integer> likes = film.getLikes();
            Integer userId = rs.getInt("user_id");
            if (userId != 0)
                likes.add(userId);
        }
        return new ArrayList<>(filmMap.values());
    };

    private void likesUpdater(Film film) {
        Set<Integer> likes = film.getLikes();
        List<MapSqlParameterSource> sqlParameterSources = new ArrayList<>();
        for (Integer likeId : likes) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("user", likeId)
                    .addValue("id", film.getId());
            sqlParameterSources.add(mapSqlParameterSource);
        }
        jdbcTemplate.batchUpdate("INSERT INTO LIKES (film_id, user_id) VALUES (:id, :user)",
                sqlParameterSources.toArray(new SqlParameterSource[0]));
    }

    private void genreUpdater(int filmId, Set<Genre> genres) {
        List<MapSqlParameterSource> sqlParameterSources = new ArrayList<>();
        for (Genre genre : genres) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("genre", genre.getId())
                    .addValue("id", filmId);
            sqlParameterSources.add(mapSqlParameterSource);
        }
        jdbcTemplate.batchUpdate("INSERT INTO FILM_GENRE (film_id, genre_id) VALUES (:id, :genre)",
                sqlParameterSources.toArray(new SqlParameterSource[0]));
    }
}
