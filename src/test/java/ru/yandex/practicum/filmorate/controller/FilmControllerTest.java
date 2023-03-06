package ru.yandex.practicum.filmorate.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//public class FilmControllerTest {
//    static Validator validator;
//    FilmController filmController;
//    Film film1;
//    Film film2;
//    Film film3;
//    InMemoryFilmService filmService;
//
//    @BeforeAll
//    public static void start() {
//        validator = buildDefaultValidatorFactory().getValidator();
//    }
//
//    @BeforeEach
//    void createFilms() {
//        filmService = new InMemoryFilmService(filmService.getFilmStorage());
//        filmController = new FilmController(filmService);
//        film1 = Film.builder()
//                .name("John Wick")
//                .description("John Wick is a 2014 American neo-noir action thriller film directed by Chad Stahelski " +
//                        "(in his feature directorial debut) and written by Derek Kolstad.")
//                .releaseDate(LocalDate.of(2014, 10, 24))
//                .duration(101)
//                .build();
//
//        film2 = Film.builder()
//                .name("The Matrix")
//                .description("The Matrix is a 1999 science fiction action film written and directed by the Wachowskis." +
//                        "It is the first installment in the Matrix film series, starring Keanu Reeves," +
//                        "Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving, and Joe Pantoliano," +
//                        "and depicts a dystopian future in which humanity is unknowingly trapped inside the Matrix," +
//                        "a simulated reality that intelligent machines have created to distract humans while using" +
//                        "their bodies as an energy source..")
//                .releaseDate(LocalDate.of(1999, 3, 31))
//                .duration(136)
//                .build();
//
//        film3 = Film.builder()
//                .name("Unforgiven")
//                .description("Unforgiven is a 1992 American Western film starring, directed," +
//                        "and produced by Clint Eastwood, and written by David Webb Peoples. The film tells the story " +
//                        "of William Munny, an aging outlaw and killer who takes on one more job, " +
//                        "years after he had turned to farming.")
//                .releaseDate(LocalDate.of(1992, 8, 7))
//                .duration(131)
//                .build();
//    }
//
//    @Test
//    void shouldCreateFilm() {
//        Film film;
//        filmController.create(film = Film.builder()
//                .name("John Wick")
//                .description("John Wick is a 2014 American neo-noir action thriller film directed by Chad Stahelski " +
//                        "(in his feature directorial debut) and written by Derek Kolstad.")
//                .releaseDate(LocalDate.of(2014, 10, 24))
//                .duration(101)
//                .build());
//        assertEquals(1, film.getId());
//    }
//
//    @Test
//    void shouldUpdateFilm() {
//        filmController.create(film1);
//        Film updateFilm = Film.builder()
//                .id(film1.getId())
//                .description("Asterix & Obelix: Mission Cleopatra is a 2002 French/Italian fantasy comedy film" +
//                        "written and directed by Alain Chabat and adapted from the comic book series Asterix" +
//                        "by René Goscinny and Albert Uderzo.")
//                .name("Asterix & Obelix: Mission Cleopatra")
//                .releaseDate(LocalDate.of(1900, 12, 24))
//                .duration(74)
//                .build();
//        filmController.update(updateFilm);
//        assertEquals(updateFilm.getName(), filmController.getFilms().stream().findAny().get().getName());
//    }
//
//    @Test
//    void shouldAddSomeFilms() {
//        filmController.create(film1);
//        filmController.create(film2);
//        filmController.create(film3);
//        assertEquals(3, filmController.getFilms().size());
//    }
//
//    @Test
//    void shouldGetExceptionIfNameIsNull() {
//        film1.setName(null);
//        Set<ConstraintViolation<Film>> violations = validator.validate(film1);
//        ConstraintViolation<Film> violation = violations.stream().findFirst()
//                .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
//        assertEquals("У фильма отсутствует название!", violation.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfTooLongDescription() {
//        film1.setDescription("John Wick is mourning the death of his wife, Helen, from a disease." +
//                "He receives a beagle puppy, a gift pre-arranged by Helen to provide him comfort in his grief." +
//                "A few days later, Wick is accosted at a gas station in the New York City outskirts by a trio of" +
//                "Russian gangsters, led by Iosef.");
//        Set<ConstraintViolation<Film>> violations = validator.validate(film1);
//        ConstraintViolation<Film> violation = violations.stream().findFirst().orElseThrow(()
//                -> new RuntimeException("Отсутствует ошибка валидации"));
//        assertEquals("Длительность описания фильма не должна быть больше 200 символов!", violation.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfWrongReleaseDate() {
//        film1.setReleaseDate(LocalDate.of(1800, 10, 25));
//        final ValidationException exception = assertThrows(ValidationException.class,
//                () -> filmController.create(film1));
//        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года!", exception.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfDurationZero() {
//        film1.setDuration(0);
//        Set<ConstraintViolation<Film>> violations = validator.validate(film1);
//        ConstraintViolation<Film> violation = violations.stream().findFirst()
//                .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
//        assertEquals("Продолжительность фильма должна быть положительной!", violation.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfDurationLessThenZero() {
//        film1.setDuration(-123);
//        Set<ConstraintViolation<Film>> violations = validator.validate(film1);
//        ConstraintViolation<Film> violation = violations.stream().findFirst()
//                .orElseThrow(() -> new RuntimeException("Отсутствует ошибка валидации"));
//        assertEquals("Продолжительность фильма должна быть положительной!", violation.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfUpdateFilmWithWrongId() {
//        filmController.create(film1);
//        Film wrongIdFilm = Film.builder()
//                .id(12)
//                .description("updated description for tests")
//                .name("UPDATING")
//                .releaseDate(LocalDate.of(2022, 4, 25))
//                .duration(99)
//                .build();
//        final ValidationException exception = assertThrows(ValidationException.class,
//                () -> filmController.update(wrongIdFilm));
//        assertEquals("Фильм с таким id не найден в коллекции!", exception.getMessage());
//    }
//
//    @Test
//    void shouldGetExceptionIfUpdateFilmWithWrongReleaseDate() {
//        filmController.create(film1);
//        Film update = Film.builder()
//                .id(film1.getId())
//                .description("updated description for tests")
//                .name("Asterix & Obelix: Mission Cleopatra")
//                .releaseDate(LocalDate.of(49, 1, 1))
//                .duration(124)
//                .build();
//        final ValidationException exception = assertThrows(ValidationException.class,
//                () -> filmController.update(update));
//        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года!", exception.getMessage());
//    }
//}
