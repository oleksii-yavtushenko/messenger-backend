package com.letter.server.mapper;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dao.entity.Status;
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
        mappedEntity.setRecipient(messageDto.getRecipient() != null ? UserMapper.INSTANCE.userDtoToEntity(messageDto.getRecipient()) : messageEntity.getRecipient());
        mappedEntity.setSender(messageDto.getSender() != null ? UserMapper.INSTANCE.userDtoToEntity(messageDto.getSender()) : messageEntity.getSender());
        mappedEntity.setCreateTime(messageDto.getCreateTime() != null ? messageDto.getCreateTime() : messageEntity.getCreateTime());
        mappedEntity.setModifyTime(messageDto.getModifyTime() != null ? messageDto.getModifyTime() : messageEntity.getModifyTime());
        mappedEntity.setIsRead(messageDto.getIsRead() != null ? messageDto.getIsRead() : messageEntity.getIsRead());
        mappedEntity.setStatus(messageDto.getStatus() != null ? Status.valueOf(messageDto.getStatus()) : messageEntity.getStatus());

        return mappedEntity;
    }
}
