package ru.yandex.practicum.filmorate.storage.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureTestDatabase
class UserDbStorageTest {

    private final UserStorage userStorage;

    public UserDbStorageTest(@Qualifier("UserDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Test
    void create() {
        User user1 = new User();
        user1.setName("userName1");
        user1.setEmail("user1@gmail.com");
        user1.setLogin("user1Login");
        user1.setBirthday(LocalDate.of(2000, 1, 1));
        User dbUser1 = userStorage.create(user1);
        assertEquals(1, dbUser1.getId());

        User user2 = new User();
        user2.setName("userName2");
        user2.setEmail("user2@gmail.com");
        user2.setLogin("user2Login");
        user2.setBirthday(LocalDate.of(2000, 2, 2));
        User dbUser2 = userStorage.create(user2);
        assertEquals(2, dbUser2.getId());
    }

    @Test
    void update() {
        User user = new User();
        user.setName("userUpdatedName");
        user.setEmail("user@gmail.com");
        user.setLogin("loginUpdated");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        user.setId(1);
        User userFromDb = userStorage.update(user);
        assertEquals(1, userFromDb.getId());
        assertEquals("userUpdatedName", userFromDb.getName());
    }

    @Test
    void getAllUsers() {
        assertEquals(2, userStorage.getAll().size());
    }

    @Test
    void getUserById() {
        assertEquals("userName2", userStorage.findUserById(2).getName());
    }

    @Test
    void getUserWithWrongId() {
        assertNull(userStorage.findUserById(-1));
    }
}