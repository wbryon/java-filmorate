DROP TABLE IF EXISTS FILM_GENRE CASCADE;
DROP TABLE IF EXISTS USERS_RELATIONSHIP CASCADE;
DROP TABLE IF EXISTS GENRES CASCADE;
DROP TABLE IF EXISTS LIKES CASCADE;
DROP TABLE IF EXISTS FILMS CASCADE;
DROP TABLE IF EXISTS MPA_RATING CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;

CREATE TABLE IF NOT EXISTS FILMS (
    film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    rating_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(200),
    release_date DATE NOT NULL,
     duration INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS MPA_RATING (
    rating_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
     category CHAR(5) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS GENRES (
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    name VARCHAR(20) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS FILM_GENRE (
    film_id INTEGER NOT NULL,
    genre_id INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS USERS (
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    login VARCHAR(200) UNIQUE NOT NULL,
    name VARCHAR(200) NOT NULL,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS USERS_RELATIONSHIP (
    user1_id INTEGER NOT NULL,
    user2_id INTEGER NOT NULL,
    status VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS LIKES (
    film_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    PRIMARY KEY (film_id, user_id)
);