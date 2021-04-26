package com.letter.server.service.validation;

import com.letter.server.service.exception.ValidationException;

public interface Validator<D> {

    void validate(D dto) throws ValidationException;

    void validateId(D dto) throws ValidationException;
}
