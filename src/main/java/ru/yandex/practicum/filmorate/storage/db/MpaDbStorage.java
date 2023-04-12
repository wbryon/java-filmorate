package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@Slf4j
public class MpaDbStorage implements MpaStorage {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    static final RowMapper<Mpa> mpaMapper = (rs, rowNum) ->
            new Mpa(rs.getInt("mpa_id"), rs.getString("category"));

    public MpaDbStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Mpa> getMpaById(int id) {
        try {
            Mpa mpa = namedParameterJdbcTemplate.queryForObject("SELECT * FROM MPA WHERE mpa_id=:id",
                    Map.of("id", id), mpaMapper);
            return Optional.ofNullable(mpa);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Mpa> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM MPA", mpaMapper);
    }
}
