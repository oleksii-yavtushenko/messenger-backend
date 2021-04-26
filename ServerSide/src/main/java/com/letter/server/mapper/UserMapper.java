package com.letter.server.mapper;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = OnlineStatusMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userDtoToEntity(UserDto userDto);

    UserDto userEntityToDto(UserEntity userEntity);

    default UserEntity editUserDtoToEntity(UserEntity userEntity, UserDto userDto) {
        UserEntity mappedEntity = new UserEntity();

        mappedEntity.setId(userEntity.getId());
        mappedEntity.setLogin(userDto.getLogin() != null ? userDto.getLogin() : userEntity.getLogin());
        mappedEntity.setPassword(userDto.getPassword() != null ? userDto.getLogin() : userEntity.getPassword());
        mappedEntity.setEmail(userDto.getEmail() != null ? userDto.getEmail() : userEntity.getPassword());
        mappedEntity.setOnlineStatus(userDto.getOnlineStatus() != null ?
                OnlineStatusMapper.INSTANCE.onlineStatusDtoToEntity(userDto.getOnlineStatus()) : userEntity.getOnlineStatus());

        return mappedEntity;
    }
}
