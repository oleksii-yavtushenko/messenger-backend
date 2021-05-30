package com.letter.server.service;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.Status;
import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dao.repository.MessageRepository;
import com.letter.server.dto.DetailedMessage;
import com.letter.server.dto.DetailedUser;
import com.letter.server.dto.Message;
import com.letter.server.dto.User;
import com.letter.server.mapper.MessageMapper;
import com.letter.server.mapper.UserMapper;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final UserMapper userMapper;

    private final UserService userService;

    private final Validator<DetailedMessage> messageValidator;

    private final Validator<DetailedUser> detailedUserValidator;

    private final Validator<User> userValidator;

    public List<DetailedMessage> findAllByTwoUsers(DetailedUser firstDetailedUser, DetailedUser secondDetailedUser) throws ServiceException {

        detailedUserValidator.validateId(firstDetailedUser);
        detailedUserValidator.validateId(secondDetailedUser);

        List<DetailedMessage> responseList;

        try {
            firstDetailedUser = userService.findByIdDetailed(firstDetailedUser.getId());
            secondDetailedUser = userService.findByIdDetailed(secondDetailedUser.getId());

            UserEntity firstUserEntity = userMapper.userDtoDetailedToEntity(firstDetailedUser);
            UserEntity secondUserEntity = userMapper.userDtoDetailedToEntity(secondDetailedUser);

            List<MessageEntity> messageEntities = messageRepository.findAllByUsers(firstUserEntity, secondUserEntity);

            responseList = messageEntities.stream()
                    .map(messageMapper::messageEntityToDetailedMessage)
                    .filter(message -> !message.getStatus().equals(Status.DELETED.getValue()))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding messages by user ids=[" + firstDetailedUser.getId() + ", " + secondDetailedUser.getId() + "]", ex);
        }

        return responseList;
    }

    public List<Message> findAllByTwoUsers(User firstUser, User secondUser) throws ServiceException {

        userValidator.validateId(firstUser);
        userValidator.validateId(secondUser);

        List<Message> responseList;

        try {
            firstUser = userService.findById(firstUser.getId());
            secondUser = userService.findById(secondUser.getId());

            UserEntity firstUserEntity = userMapper.userDtoToEntity(firstUser);
            UserEntity secondUserEntity = userMapper.userDtoToEntity(secondUser);

            List<MessageEntity> messageEntities = messageRepository.findAllByUsers(firstUserEntity, secondUserEntity);

            responseList = messageEntities.stream()
                    .map(messageMapper::messageEntityToMessage)
                    .filter(message -> !message.getStatus().equals(Status.DELETED.getValue()))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding messages by user ids=[" + firstUser.getId() + ", " + secondUser.getId() + "]", ex);
        }

        return responseList;
    }

    public DetailedMessage findById(Long id) throws ServiceException {
        DetailedMessage response;

        try {
            MessageEntity messageEntity = messageRepository.findById(id).orElseThrow(EntityNotFoundException::new);

            response = messageMapper.messageEntityToDetailedMessage(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while sending message, id=" + id, ex);
        }

        return response;
    }

    public DetailedMessage sendDetailed(DetailedMessage detailedMessage) throws ServiceException {

        messageValidator.validate(detailedMessage);

        DetailedMessage response;

        try {
            MessageEntity messageEntity = messageMapper.detailedMessageToMessageEntity(detailedMessage);
            messageEntity.setStatus(Status.SENT);

            messageEntity = messageRepository.save(messageEntity);

            response = messageMapper.messageEntityToDetailedMessage(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while sending message, sender=" + detailedMessage.getSender() + ", recipient=" + detailedMessage.getRecipient(), ex);
        }

        return response;
    }

    public Message send(Message message) throws ServiceException {

        Message response;

        try {
            MessageEntity messageEntity = messageMapper.messageToMessageEntity(message);
            messageEntity.setStatus(Status.SENT);

            messageEntity = messageRepository.save(messageEntity);

            response = messageMapper.messageEntityToMessage(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while sending message, sender=" + message.getSenderId() + ", recipient=" + message.getRecipientId(), ex);
        }

        return response;
    }

    public DetailedMessage read(DetailedMessage detailedMessage) throws ServiceException {

        messageValidator.validateId(detailedMessage);

        DetailedMessage response;

        try {
            MessageEntity messageEntity = messageRepository.findById(detailedMessage.getId()).orElseThrow(EntityNotFoundException::new);
            messageEntity.setIsRead(true);

            messageEntity = messageRepository.save(messageEntity);

            response = messageMapper.messageEntityToDetailedMessage(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while reading message, id=" + detailedMessage.getId(), ex);
        }

        return response;
    }

    public DetailedMessage edit(DetailedMessage detailedMessage) throws ServiceException {

        messageValidator.validateId(detailedMessage);

        DetailedMessage response;

        try {
            MessageEntity messageEntity = messageRepository.findById(detailedMessage.getId()).orElseThrow(EntityNotFoundException::new);

            MessageEntity toSave = messageMapper.editMessageDtoToEntity(messageEntity, detailedMessage);

            toSave.setModifyTime(OffsetDateTime.now());
            toSave.setStatus(Status.EDITED);

            messageEntity = messageRepository.save(toSave);

            response = messageMapper.messageEntityToDetailedMessage(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while editing message, id=" + detailedMessage.getId(), ex);
        }

        return response;
    }

    public void delete(DetailedMessage detailedMessage) throws ServiceException {

        messageValidator.validateId(detailedMessage);

        try {
            MessageEntity messageEntity = messageRepository.findById(detailedMessage.getId()).orElseThrow(EntityNotFoundException::new);
            messageEntity.setStatus(Status.DELETED);

            messageRepository.save(messageEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while deleting message, id=" + detailedMessage.getId(), ex);
        }
    }
}
