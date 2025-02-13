package ru.yandex.practicum.filmorate.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Filmorate API",
                version = "1.0",
                description = "Документация API для Filmorate"
        )
)
public class SwaggerConfig {
}
