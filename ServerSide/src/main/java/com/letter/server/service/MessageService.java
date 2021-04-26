package com.letter.server.service;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.Status;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.MessageRepository;
import com.letter.server.dto.MessageDto;
import com.letter.server.dto.UserDto;
import com.letter.server.mapper.MessageMapper;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final Validator<MessageDto> messageValidator;

    private final UserService userService;

    private final UserMapper userMapper;

    private final Validator<UserDto> userValidator;

    public List<MessageDto> findAllByTwoUsers(UserDto firstUserDto, UserDto secondUserDto) throws ServiceException {
        userValidator.validateId(firstUserDto);
        userValidator.validateId(secondUserDto);

        List<MessageDto> responseList;

        try {
            firstUserDto = userService.findById(firstUserDto);
            secondUserDto = userService.findById(secondUserDto);

            UserEntity firstUserEntity = userMapper.userDtoToEntity(firstUserDto);
            UserEntity secondUserEntity = userMapper.userDtoToEntity(secondUserDto);

            List<MessageEntity> messageEntities = messageRepository.findAllByUsers(firstUserEntity, secondUserEntity);

            responseList = messageEntities.stream()
                    .map(messageMapper::messageEntityToDto)
                    .filter(message -> !message.getStatus().equals(Status.DELETED.getValue()))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding messages by user ids=[" + firstUserDto.getId() + ", " + secondUserDto.getId() + "]", ex);
        }

        return responseList;
    }

    public MessageDto sendMessage(MessageDto messageDto) throws ServiceException {
        messageValidator.validate(messageDto);

        MessageDto response;

        try {
            MessageEntity messageEntity = messageMapper.messageDtoToEntity(messageDto);
            messageEntity.setStatus(Status.SENT);

            messageEntity = messageRepository.save(messageEntity);

            response = messageMapper.messageEntityToDto(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while sending message, senderId=" + messageDto.getSender() + ", recipientId=" + messageDto.getRecipient(), ex);
        }

        return response;
    }

    public MessageDto readMessage(MessageDto messageDto) throws ServiceException {
        messageValidator.validate(messageDto);

        MessageDto response;

        try {
            MessageEntity messageEntity = messageMapper.messageDtoToEntity(messageDto);
            messageEntity.setIsRead(true);

            messageEntity = messageRepository.save(messageEntity);

            response = messageMapper.messageEntityToDto(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while reading message, id=" + messageDto.getId(), ex);
        }

        return response;
    }

    public MessageDto editMessage(MessageDto messageDto) throws ServiceException {
        messageValidator.validate(messageDto);

        MessageDto response;

        try {
            MessageEntity messageEntity = messageMapper.messageDtoToEntity(messageDto);
            messageEntity.setModifyTime(OffsetDateTime.now());
            messageEntity.setStatus(Status.EDITED);

            messageEntity = messageRepository.save(messageEntity);

            response = messageMapper.messageEntityToDto(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while editing message, id=" + messageDto.getId(), ex);
        }

        return response;
    }

    public void delete(MessageDto messageDto) throws ServiceException {
        messageValidator.validate(messageDto);

        try {
            MessageEntity messageEntity = messageMapper.messageDtoToEntity(messageDto);
            messageEntity.setStatus(Status.DELETED);

            messageRepository.delete(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while editing message, id=" + messageDto.getId(), ex);
        }
    }
}
