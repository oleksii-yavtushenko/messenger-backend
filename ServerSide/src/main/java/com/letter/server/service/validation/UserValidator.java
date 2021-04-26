package com.letter.server.service.validation;

import com.letter.server.dto.UserDto;
import com.letter.server.service.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Validator<UserDto> {

    @Override
    public void validate(UserDto dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("UserDto object cannot be null");
        }

        if (dto.getLogin() == null && dto.getPassword() == null && dto.getEmail() == null) {
            throw new ValidationException("UserDto object's fields cannot be null, login=" + dto.getLogin());
        }
    }

    @Override
    public void validateId(UserDto dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("UserDto object cannot be null");
        }

        if (dto.getId() == null) {
            throw new ValidationException("UserDto object's id cannot be null");
        }
    }
}
