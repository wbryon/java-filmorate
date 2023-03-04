package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final HttpStatus httpStatus;
    private final String error;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalDateTime time = LocalDateTime.now();

    public ErrorResponse(HttpStatus httpStatus, String error) {
        this.httpStatus = httpStatus;
        this.error = error;
    }
}
