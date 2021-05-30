package com.letter.server.service.validation;

import com.letter.server.dto.DetailedMessage;
import com.letter.server.service.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class MessageValidator implements Validator<DetailedMessage> {

    @Override
    public void validate(DetailedMessage dto) throws ValidationException {
        if (dto != null && dto.getMessageText() != null && dto.getRecipient() != null && dto.getSender() != null) {
            return;
        }
        throw new ValidationException("MessageDto object's fields cannot be null");
    }

    @Override
    public void validateId(DetailedMessage dto) throws ValidationException {
        if (dto == null) {
            throw new ValidationException("MessageDto object cannot be null");
        }

        if (dto.getId() == null) {
            throw new ValidationException("MessageDto object's id cannot be null");
        }
    }
}
