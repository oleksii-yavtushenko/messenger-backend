package com.letter.server.service.validation;

import com.letter.server.dto.OnlineStatusDto;
import com.letter.server.service.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class OnlineStatusValidator implements Validator<OnlineStatusDto> {

    @Override
    public void validate(OnlineStatusDto dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("OnlineStatusDto object cannot be null");
        }

        if (dto.getIsOnline() == null && dto.getUserId() == null) {
            throw new ValidationException("OnlineStatusDto object's fields cannot be null, userId=" + dto.getUserId());
        }
    }

    @Override
    public void validateId(OnlineStatusDto dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("OnlineStatusDto object cannot be null");
        }

        if (dto.getId() == null ) {
            throw new ValidationException("OnlineStatusDto object's id cannot be null");
        }
    }
}
