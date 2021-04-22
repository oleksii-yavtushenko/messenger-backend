package com.letter.server.mapper;

import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dto.OnlineStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OnlineStatusMapper {

    @Mapping(source="userId", target="user.id")
    OnlineStatusEntity onlineStatusDtoToEntity(OnlineStatusDto onlineStatusDto);

    @Mapping(source="user.id", target = "userId")
    OnlineStatusDto onlineStatusEntityToDto(OnlineStatusEntity onlineStatusEntity);
}
