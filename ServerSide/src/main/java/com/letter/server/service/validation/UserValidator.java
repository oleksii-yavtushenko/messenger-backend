package com.letter.server.service.validation;

import com.letter.server.dto.User;
import com.letter.server.service.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User dto) throws ValidationException {
        checkNull(dto);
        checkNull(dto.getId());
        checkNull(dto.getLogin());
    }

    @Override
    public void validateId(User dto) throws ValidationException {
        checkNull(dto);
        checkNull(dto.getId());
    }

    private void checkNull(Object object) throws ValidationException {
        if (object == null) {
            throw new ValidationException("Object or its fields cannot be null");
        }
    }
}
