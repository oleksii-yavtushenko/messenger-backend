package com.letter.server.mapper;

import com.letter.server.dao.entity.UserEntity;
import com.letter.server.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = OnlineStatusMapper.class)
public interface UserMapper {

    UserEntity userDtoToEntity(UserDto userDto);

    UserDto userEntityToDto(UserEntity userEntity);
}
