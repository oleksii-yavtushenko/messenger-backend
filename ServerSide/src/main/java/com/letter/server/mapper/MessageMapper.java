package com.letter.server.mapper;

import com.letter.server.dao.entity.MessageEntity;
import com.letter.server.dto.MessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface MessageMapper {

    MessageEntity messageDtoToEntity(MessageDto messageDto);

    MessageDto messageEntityToDto(MessageEntity messageEntity);
}
