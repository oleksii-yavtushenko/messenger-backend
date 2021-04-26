package com.letter.server.mapper;

import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dto.OnlineStatusDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OnlineStatusMapper {

    OnlineStatusMapper INSTANCE = Mappers.getMapper(OnlineStatusMapper.class);

    @Mapping(source="userId", target="user.id")
    OnlineStatusEntity onlineStatusDtoToEntity(OnlineStatusDto onlineStatusDto);

    @Mapping(source="user.id", target = "userId")
    OnlineStatusDto onlineStatusEntityToDto(OnlineStatusEntity onlineStatusEntity);

    default OnlineStatusEntity editOnlineStatusDtoToEntity(OnlineStatusEntity onlineStatusEntity, OnlineStatusDto onlineStatusDto) {
        OnlineStatusEntity mappedEntity = new OnlineStatusEntity();

        mappedEntity.setId(onlineStatusEntity.getId());
        mappedEntity.setUser(onlineStatusEntity.getUser());
        mappedEntity.setIsOnline(onlineStatusDto.getIsOnline() != null ? onlineStatusDto.getIsOnline() : onlineStatusEntity.getIsOnline());
        mappedEntity.setLastOnlineTime(onlineStatusDto.getLastOnlineTime() != null ? onlineStatusDto.getLastOnlineTime() : onlineStatusEntity.getLastOnlineTime());

        return mappedEntity;
    }
}
