package ru.yandex.practicum.filmorate.storage.impl.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@Component
@Primary
public class DbUserStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public DbUserStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO USERS VALUES (?,?,?,?,?)", user.getId(), user.getEmail(), user.getLogin(),
                user.getName(), user.getBirthday());
    }

    @Override
    public void update(User user) {

    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", new UserMapper());
    }

    @Override
    public User findUserById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE user_id=?", User.class, id);
    }
}
