package ru.yandex.practicum.filmorate.model.validator;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ConstraintName, User> {

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getName() == null || user.getName().isBlank())
            user.setName(user.getLogin());
        return true;
    }
}
