package ru.yandex.practicum.filmorate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//public class UserControllerTest {
//    static Validator validator;
//    UserController userController;
//    User user1;
//    User user2;
//    User user3;
//    InMemoryUserService userService;
//
//    @BeforeAll
//    public static void start() {
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
//
//    @BeforeEach
//    void createUsers() {
//        userService = new InMemoryUserService(userService.getUserStorage());
//        userController = new UserController(userStorage, userService);
//        user1 = User.builder()
//                .name("name1")
//                .email("111@111.ru")
//                .login("login1")
//                .birthday(LocalDate.of(2001, 1, 21))
//                .build();
//
//        user2 = User.builder()
//                .name("name2")
//                .email("222@222.ru")
//                .login("login2")
//                .birthday(LocalDate.of(2002, 2, 22))
//                .build();
//
//        user3 = User.builder()
//                .name("name3")
//                .email("333@333.ru")
//                .login("login3")
//                .birthday(LocalDate.of(2003, 3, 23))
//                .build();
//    }
//
//    @Test
//    void shouldCreateUser() {
//        userController.create(user1);
//        assertEquals(1, user1.getId());
//    }
//
//    @Test
//    void shouldCreateSomeUsers() {
//        userController.create(user1);
//        userController.create(user2);
//        userController.create(user3);
//        assertEquals(3, userController.getUsers().size());
//    }
//
//    @Test
//    void shouldCreateIfNameIsNull() {
//        user1.setName(null);
//        userController.create(user1);
//        assertEquals("login1", user1.getName());
//    }
//
//    @Test
//    void shouldCreateIfNameIsBlank() {
//        user1.setName("   ");
//        userController.create(user1);
//        assertEquals("login1", user1.getName());
//    }
//
//    @Test
//    void shouldUpdateUser() {
//        userController.create(user1);
//        User updateUser1 = User.builder()
//                .id(user1.getId())
//                .email("updateUser1@111.com")
//                .login("updatedLogin1")
//                .name("updatedName1")
//                .birthday(LocalDate.of(2012, 9, 11))
//                .build();
//        userController.update(updateUser1);
//        assertEquals(updateUser1.getLogin(), userController.getUsers().stream().findAny().get().getLogin());
//    }
//
//    @Test
//    void shouldUpdateUserIfNameIsNull() {
//        userController.create(user1);
//        user1.setName(null);
//        userController.update(user1);
//        assertEquals("login1", user1.getName());
//    }
//
//    @Test
//    void shouldGetExceptionWithWrongEmail() {
//        user1.setEmail("111.ru");
//        Set<ConstraintViolation<User>> violations = validator.validate(user1);
//        ConstraintViolation<User> violation = violations.stream().findFirst()
//                .orElseThrow(() -> new ValidationException("?????????????????? ???????????? ??????????????????!"));
//        assertEquals("???????????????????? ?????????????????????? ?????????? - ?????????????????????? ???????????? '@'", violation.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfLoginIsNull() {
//        user1.setLogin(null);
//        Set<ConstraintViolation<User>> errors = validator.validate(user1);
//        ConstraintViolation<User> error = errors.stream().findFirst()
//                .orElseThrow(() -> new ValidationException("?????????????????? ???????????? ??????????????????!"));
//        assertEquals("?????????? ???????????????????????? ???? ?????????? ???????? ????????????!", error.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfLoginContainsSpaces() {
//        user1.setLogin("my login");
//        final ValidationException exception = assertThrows(ValidationException.class,
//                () -> userController.create(user1));
//        assertEquals("?????????? ???????????????????????? ???? ?????????? ?????????????????? ??????????????!", exception.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfBirthdayInFuture() {
//        user1.setBirthday(LocalDate.of(3023, 1, 1));
//        Set<ConstraintViolation<User>> violations = validator.validate(user1);
//        ConstraintViolation<User> violation = violations.stream().findFirst()
//                .orElseThrow(() -> new ValidationException("?????????????????? ???????????? ??????????????????!"));
//        assertEquals("???????? ???????????????? ???? ?????????? ???????? ?? ??????????????!", violation.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionWhenUpdateIfLoginContainsSpaces() {
//        user1.setLogin("login     1");
//        ValidationException exception = assertThrows(ValidationException.class, () -> userController.update(user1));
//        assertEquals("?????????? ???????????????????????? ???? ?????????? ?????????????????? ??????????????!", exception.getMessage());
//    }
//}
