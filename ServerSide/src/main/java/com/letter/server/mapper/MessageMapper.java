package com.letter.server.mapper;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dto.DetailedMessage;
import com.letter.server.dto.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageEntity detailedMessageToMessageEntity(DetailedMessage detailedMessage);

    DetailedMessage messageEntityToDetailedMessage(MessageEntity messageEntity);

    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "recipient.id", target = "recipientId")
    Message messageEntityToMessage(MessageEntity messageEntity);

    @Mapping(source = "senderId", target = "sender.id")
    @Mapping(source = "recipientId", target = "recipient.id")
    MessageEntity messageToMessageEntity(Message message);

    default MessageEntity editMessageDtoToEntity(MessageEntity messageEntity, DetailedMessage detailedMessage) {
        MessageEntity mappedEntity = new MessageEntity();

        mappedEntity.setId(messageEntity.getId());
        mappedEntity.setMessageText(detailedMessage.getMessageText() != null ? detailedMessage.getMessageText() : messageEntity.getMessageText());
        mappedEntity.setRecipient(messageEntity.getRecipient());
        mappedEntity.setSender(messageEntity.getSender());
        mappedEntity.setCreateTime(messageEntity.getCreateTime());
        mappedEntity.setIsRead(messageEntity.getIsRead());
        mappedEntity.setStatus(messageEntity.getStatus());

        return mappedEntity;
    }
}
