package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component("DbUserStorage")
public class UserDbStorage implements UserStorage {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserDbStorage(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void create(User user) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("birthday", user.getBirthday());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO USERS (email, login, name, birthday)" +
                "VALUES (:email, :login, :name, :birthday)", sqlParameterSource, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
    }

    @Override
    public void update(User user) {
        Integer userId = user.getId();
        try {
            namedParameterJdbcTemplate.queryForObject("SELECT user_id FROM USERS " +
                    "WHERE user_id=:id", Map.of("id", userId), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn("Пользователь с id = " + userId + " не найден!");
            throw new UserNotFoundException("");
        }
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("name", user.getName())
                .addValue("birthday", user.getBirthday());
        namedParameterJdbcTemplate.update("UPDATE USERS SET email=:email, login=:login, name=:name, " +
                "birthday=:birthday WHERE user_id=:id", sqlParameterSource);
        namedParameterJdbcTemplate.update("DELETE FROM USERS_RELATIONSHIP WHERE user_id = :id",
                Map.of("id", user.getId()));
    }

    @Override
    public List<User> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM USERS", new UserMapper());
    }

    @Override
    public User findUserById(int id) {
        return namedParameterJdbcTemplate.query("SELECT * FROM USERS WHERE user_id=:id", Map.of("id", id),
                        new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }

    private static final class UserMapper implements ResultSetExtractor<List<User>> {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                try {
                    User newUser = new User();
                    newUser.setId(rs.getInt("user_id"));
                    newUser.setEmail(rs.getString("email"));
                    newUser.setName(rs.getString("name"));
                    newUser.setLogin(rs.getString("login"));
                    newUser.setBirthday(rs.getDate("birthday").toLocalDate());
                    users.add(newUser);
                } catch (SQLException e) {
                    throw new RuntimeException("Неверный запрос" + e);
                }
            }
            return users;
        }
    }
}
