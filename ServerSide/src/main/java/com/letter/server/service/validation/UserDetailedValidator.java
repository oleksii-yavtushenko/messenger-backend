package com.letter.server.service.validation;

import com.letter.server.dto.DetailedUser;
import com.letter.server.service.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailedValidator implements Validator<DetailedUser> {

    private static final String OBJECT_CANNOT_BE_NULL = "UserDto object cannot be null";

    @Override
    public void validate(DetailedUser dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException(OBJECT_CANNOT_BE_NULL);
        }

        if (dto.getLogin() == null && dto.getPassword() == null && dto.getEmail() == null) {
            throw new ValidationException("UserDto object's fields cannot be null, login=" + dto.getLogin());
        }
    }

    @Override
    public void validateId(DetailedUser dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException(OBJECT_CANNOT_BE_NULL);
        }

        if (dto.getId() == null) {
            throw new ValidationException("UserDto object's id cannot be null");
        }
    }

    public void validateLogin(DetailedUser dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException(OBJECT_CANNOT_BE_NULL);
        }

        if (dto.getLogin() == null || dto.getPassword() == null) {
            throw new ValidationException("UserDto object's field cannot be null");
        }
    }
}
