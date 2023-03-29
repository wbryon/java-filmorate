CREATE TABLE IF NOT EXISTS USERS (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    login VARCHAR(255),
    email VARCHAR(255),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS USERS_RELATIONSHIP (
    user1_id INT,
    user2_id INT,
    status VARCHAR(255),
    PRIMARY KEY(user1_id, user2_id),
    FOREIGN KEY(user1_id) REFERENCES USERS(user_id),
    FOREIGN KEY(user2_id) REFERENCES USERS(user_id)
);

CREATE TABLE IF NOT EXISTS RATING (
    rating_id INT AUTO_INCREMENT PRIMARY KEY,
    category CHAR(5)
);

CREATE TABLE IF NOT EXISTS FILMS (
    film_id INT AUTO_INCREMENT PRIMARY KEY,
    duration INT,
    rating_id INT,
    FOREIGN KEY(rating_id) REFERENCES RATING(rating_id)
);
