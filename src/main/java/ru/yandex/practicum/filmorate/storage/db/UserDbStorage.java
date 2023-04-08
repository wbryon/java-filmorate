package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
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
    public User create(User user) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("birthday", user.getBirthday());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update("INSERT INTO USERS (email, login, name, birthday)" +
                "VALUES (:email, :login, :name, :birthday)", sqlParameterSource, keyHolder);
        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();
        user.setId(id);
        return findUserById(id);
    }

    @Override
    public User update(User user) {
        Integer userId = user.getId();
        try {
            namedParameterJdbcTemplate.queryForObject("SELECT user_id FROM USERS WHERE user_id=:id",
                    Map.of("id", userId), Integer.class);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UserNotFoundException("Неверный id");
        }
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("login", user.getLogin())
                .addValue("birthday", user.getBirthday());
        namedParameterJdbcTemplate.update("UPDATE USERS SET email=:email, login=:login, name=:name, birthday=:birthday " +
                "WHERE user_id=:id", sqlParameterSource);
        Map<Integer, String> friends = user.getFriends();
        namedParameterJdbcTemplate.update("DELETE FROM USERS_RELATIONSHIP WHERE user_id = :id",
                Map.of("id", user.getId()));
        List<MapSqlParameterSource> sqlParameterSources = new ArrayList<>();
        for (Map.Entry<Integer, String> friend : friends.entrySet()) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("friend_id", friend.getKey())
                    .addValue("status", friend.getValue())
                    .addValue("id", user.getId());
            sqlParameterSources.add(mapSqlParameterSource);
        }
        SqlParameterSource[] sources = sqlParameterSources.toArray(new SqlParameterSource[0]);
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO USERS_RELATIONSHIP (user_id, friend_id, status) " +
                "VALUES (:id, :friend_id, :status)", sources);
        return findUserById(user.getId());
    }

    @Override
    public List<User> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM USERS LEFT JOIN USERS_RELATIONSHIP " +
                "ON USERS.user_id=USERS_RELATIONSHIP.user_id", new UserMapper());
    }

    @Override
    public User findUserById(int id) {
        List<User> userList = namedParameterJdbcTemplate.query("SELECT * FROM USERS LEFT JOIN USERS_RELATIONSHIP " +
                        "ON USERS.user_id=USERS_RELATIONSHIP.user_id WHERE USERS.user_id = :id",
                Map.of("id", id), new UserMapper());
        if (userList.isEmpty())
            return null;
        return userList.get(0);
    }

    private static final class UserMapper implements ResultSetExtractor<List<User>> {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            Map<Integer, User> map = new HashMap<>();
            User user;
            while (rs.next()) {
                Integer userId = rs.getInt("user_id");
                user = map.computeIfAbsent(userId, id -> {
                    try {
                        User newUser = new User();
                        newUser.setId(rs.getInt("user_id"));
                        newUser.setName(rs.getString("name"));
                        newUser.setEmail(rs.getString("email"));
                        newUser.setLogin(rs.getString("login"));
                        newUser.setBirthday(rs.getDate("birthday").toLocalDate());
                        return newUser;
                    } catch (SQLException e) {
                        throw new RuntimeException("Неверный запрос" + e);
                    }
                });
                Map<Integer, String> friendMap = user.getFriends();
                Integer friendId = rs.getInt("friend_id");
                if (friendId != 0)
                    friendMap.put(friendId, rs.getString("status"));
            }
            return new ArrayList<>(map.values());
        }
    }
}
