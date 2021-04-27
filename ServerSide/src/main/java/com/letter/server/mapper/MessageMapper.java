package com.letter.server.mapper;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dto.MessageDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageEntity messageDtoToEntity(MessageDto messageDto);

    MessageDto messageEntityToDto(MessageEntity messageEntity);

    default MessageEntity editMessageDtoToEntity(MessageEntity messageEntity, MessageDto messageDto) {
        MessageEntity mappedEntity = new MessageEntity();

        mappedEntity.setId(messageEntity.getId());
        mappedEntity.setMessageText(messageDto.getMessageText() != null ? messageDto.getMessageText() : messageEntity.getMessageText());
        mappedEntity.setRecipient(messageEntity.getRecipient());
        mappedEntity.setSender(messageEntity.getSender());
        mappedEntity.setCreateTime(messageEntity.getCreateTime());
        mappedEntity.setIsRead(messageEntity.getIsRead());
        mappedEntity.setStatus(messageEntity.getStatus());

        return mappedEntity;
    }
}
